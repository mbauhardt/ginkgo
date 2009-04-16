package ginkgo.webapp.persistence.service;

import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.model.BuildCommand;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildCommand.Status;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("buildNumberTrigger")
public class BuildNumberTrigger implements ITriggerService<BuildNumber> {

    private static final Logger LOG = LoggerFactory.getLogger(BuildNumberTrigger.class);

    private final ITriggerService<BuildCommand> _triggerService;

    private boolean LOCK = false;

    @Autowired
    public BuildNumberTrigger(@Qualifier("buildCommandTrigger") ITriggerService<BuildCommand> triggerService) {
        _triggerService = triggerService;
    }

    public boolean trigger(BuildNumber buildNumber) throws DaoException {
        boolean success = false;
        if (!LOCK) {
            LOCK = true;
            BuildCommand buildCommand = buildNumber.getBuildCommand();
            Map<String, Status> buildAgentStatus = buildCommand.getBuildAgentStatus();
            if (buildAgentStatus.isEmpty()) {
                LOG.info("trigger build command: " + buildCommand.getId());
                success = _triggerService.trigger(buildCommand);
            }
            LOCK = false;
        } else {
            LOG.info("dont trigger any build command because another build number is running");
        }
        return success;
    }

}
