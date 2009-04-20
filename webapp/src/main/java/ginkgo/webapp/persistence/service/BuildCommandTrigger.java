package ginkgo.webapp.persistence.service;

import ginkgo.api.IBuildAgent;
import ginkgo.shared.BuildStatus;
import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildCommandDao;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;
import ginkgo.webapp.persistence.model.Project;
import ginkgo.webapp.server.AgentRepository;
import ginkgo.webapp.server.AgentRepositoryEntry;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("buildCommandTrigger")
public class BuildCommandTrigger implements ITriggerService<BuildCommand> {

    private static final int MAX_RESPONSE_WAITING_TIME = 1000 * 60 * 30;
    private static final int RESPONSE_WAITING_TIME = 1000 * 5;

    private static final Logger LOG = LoggerFactory.getLogger(BuildCommandTrigger.class);

    private final AgentRepository _agentRepository;
    private final PersistenceService _persistenceService;
    private static final Object _mutex = new Object();

    private final IBuildCommandDao _buildCommandDao;
    private final IBuildNumberDao _buildNumberDao;

    @Autowired
    public BuildCommandTrigger(AgentRepository agentRepository, IBuildCommandDao buildCommandDao,
            IBuildNumberDao buildNumberDao, PersistenceService persistenceService) {
        _agentRepository = agentRepository;
        _buildCommandDao = buildCommandDao;
        _buildNumberDao = buildNumberDao;
        _persistenceService = persistenceService;
    }

    public boolean trigger(BuildCommand buildCommand) throws DaoException {
        List<AgentRepositoryEntry> list = _agentRepository.getEntries();
        boolean success = false;
        if (!list.isEmpty()) {
            for (AgentRepositoryEntry agentRepositoryEntry : list) {
                String agentName = agentRepositoryEntry.getAgentName();
                _persistenceService.beginTransaction();
                LOG.info("send buildCommand [" + buildCommand.getCommand() + " (" + buildCommand.getId()
                        + ")], buildAgent [" + agentName + "]: " + BuildStatus.NOT_RUNNING);
                buildCommand.addStatus(agentName, BuildStatus.NOT_RUNNING);
                BuildCommand firstBuildCommand = _buildCommandDao.getFirstBuildCommand(buildCommand);
                BuildNumber buildNumber = _buildNumberDao.getByBuildCommand(firstBuildCommand);
                BuildPlan buildPlan = buildNumber.getBuildPlan();
                Project project = buildPlan.getProject();
                _persistenceService.commitTransaction();
                _persistenceService.close();
                IBuildAgent buildAgent = agentRepositoryEntry.getBuildAgent();
                buildAgent.execute(project.getName(), buildCommand.getId(), buildCommand.getCommand());
            }
            success = waitOfAgentsResponse(buildCommand, System.currentTimeMillis(), System.currentTimeMillis());
            if (success) {
                BuildCommand nextBuild = buildCommand.getNextBuildCommand();
                if (nextBuild != null) {
                    trigger(nextBuild);
                }
            }
        } else {
            LOG.info("No build agent available. Don't build anything");
        }
        return success;
    }

    private boolean waitOfAgentsResponse(BuildCommand buildCommand, long startTime, long waitingTime)
            throws DaoException {
        if (waitingTime >= (startTime + MAX_RESPONSE_WAITING_TIME)) {
            return false;
        }
        try {
            synchronized (_mutex) {
                _mutex.wait(RESPONSE_WAITING_TIME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _persistenceService.beginTransaction();
        buildCommand = _buildCommandDao.getById(buildCommand.getId());
        Map<String, BuildStatus> buildAgentStatus = buildCommand.getBuildAgentStatus();
        _persistenceService.commitTransaction();
        _persistenceService.close();
        boolean success = true;
        Set<String> keySet = buildAgentStatus.keySet();
        for (String agentName : keySet) {
            BuildStatus buildStatus = buildAgentStatus.get(agentName);
            LOG.info("receive buildCommand [" + buildCommand.getCommand() + " (" + buildCommand.getId()
                    + ")], buildAgent [" + agentName + "]: " + buildStatus);
            switch (buildStatus) {
            case NOT_RUNNING:
            case RUNNING:
                waitOfAgentsResponse(buildCommand, startTime, waitingTime + RESPONSE_WAITING_TIME);
                break;
            case FAILURE:
                success = false;
            default:
                break;
            }
        }
        return success;
    }

}
