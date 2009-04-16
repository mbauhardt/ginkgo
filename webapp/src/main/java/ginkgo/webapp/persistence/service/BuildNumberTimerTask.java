package ginkgo.webapp.persistence.service;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BuildNumberTimerTask extends TimerTask {

    private final IBuildPlanDao _buildPlanDao;
    private final IBuildNumberDao _buildNumberDao;
    private final ITriggerService<BuildNumber> _triggerService;
    private static final Logger LOG = LoggerFactory.getLogger(BuildNumberTimerTask.class);
    private final PersistenceService _persistenceService;

    @Autowired
    public BuildNumberTimerTask(IBuildPlanDao buildPlanDao, IBuildNumberDao buildNumberDao,
            @Qualifier("buildNumberTrigger") ITriggerService<BuildNumber> triggerService,
            PersistenceService persistenceService) {
        _buildPlanDao = buildPlanDao;
        _buildNumberDao = buildNumberDao;
        _triggerService = triggerService;
        _persistenceService = persistenceService;
        new Timer(true).schedule(this, new Date(), 10 * 1000);
    }

    @Override
    public void run() {
        try {
            _persistenceService.beginTransaction();
            List<BuildPlan> buildPlans = _buildPlanDao.getAll();
            for (BuildPlan buildPlan : buildPlans) {
                if (!buildPlan.getBuildNumbers().isEmpty()) {
                    BuildNumber buildNumber = _buildNumberDao.getHighestBuildNumber(buildPlan);
                    LOG.debug("try to trigger build number [" + buildNumber.getBuildNumber() + "] of build plan: "
                            + buildPlan.getName());
                    _triggerService.trigger(buildNumber);
                }
            }
            _persistenceService.commitTransaction();
        } catch (DaoException e) {
            e.printStackTrace();
        } finally {
            _persistenceService.close();
        }
    }
}
