package ginkgo.webapp.persistence.service;

import ginkgo.api.IBuildAgent;
import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildCommandDao;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildCommand.Status;
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

    private static final Logger LOG = LoggerFactory.getLogger(BuildCommandTrigger.class);

    private final AgentRepository _agentRepository;
    private final PersistenceService _persistenceService;
    private static final Object _mutex = new Object();
    private final IBuildCommandDao _buildCommandDao;

    @Autowired
    public BuildCommandTrigger(AgentRepository agentRepository, IBuildCommandDao buildCommandDao,
            PersistenceService persistenceService) {
        _agentRepository = agentRepository;
        _buildCommandDao = buildCommandDao;
        _persistenceService = persistenceService;
    }

    public boolean trigger(BuildCommand buildCommand) throws DaoException {
        String command = buildCommand.getCommand();
        List<AgentRepositoryEntry> list = _agentRepository.getEntries();
        boolean success = false;
        if (!list.isEmpty()) {
            LOG.info("try to trigger command: '" + command + "' on agents " + list);
            for (AgentRepositoryEntry agentRepositoryEntry : list) {
                String agentName = agentRepositoryEntry.getAgentName();
                _persistenceService.beginTransaction();
                LOG.info("add agent [" + agentName + "] with status [" + Status.NOT_RUNNING + "] to buildCommand ["
                        + buildCommand.getCommand() + "]");
                buildCommand.addStatus(agentName, Status.NOT_RUNNING);
                _persistenceService.commitTransaction();
                IBuildAgent buildAgent = agentRepositoryEntry.getBuildAgent();
                buildAgent.execute(command);
            }
            success = waitOfAgentsResponse(buildCommand);
            if (success) {
                BuildCommand nextBuild = buildCommand.getNextBuild();
                if (nextBuild != null) {
                    trigger(nextBuild);
                }
            }
        } else {
            LOG.info("No build agent available. Don't build anything");
        }
        return success;
    }

    private boolean waitOfAgentsResponse(BuildCommand buildCommand) throws DaoException {
        LOG.info("wait of agent response from build command: " + buildCommand.getCommand());
        try {
            synchronized (_mutex) {
                _mutex.wait(1000 * 30);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _persistenceService.beginTransaction();
        buildCommand = _buildCommandDao.getById(buildCommand.getId());
        _persistenceService.commitTransaction();
        Map<String, Status> buildAgentStatus = buildCommand.getBuildAgentStatus();
        boolean success = true;
        Set<String> keySet = buildAgentStatus.keySet();
        for (String agentName : keySet) {
            Status status = buildAgentStatus.get(agentName);
            switch (status) {
            case NOT_RUNNING:
            case RUNNING:
                waitOfAgentsResponse(buildCommand);
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
