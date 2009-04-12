package ginkgo.webapp.persistence.service;

import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildNumberDao;
import ginkgo.webapp.persistence.dao.IBuildPlanDao;
import ginkgo.webapp.persistence.model.BuildNumber;
import ginkgo.webapp.persistence.model.BuildPlan;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BuildNumberTimerTask extends TimerTask {

    private final IBuildPlanDao _buildPlanDao;
    private final IBuildNumberDao _buildNumberDao;
    private final ITriggerService<BuildNumber> _triggerService;

    @Autowired
    public BuildNumberTimerTask(IBuildPlanDao buildPlanDao, IBuildNumberDao buildNumberDao,
            @Qualifier("buildNumberTrigger") ITriggerService<BuildNumber> triggerService) {
        _buildPlanDao = buildPlanDao;
        _buildNumberDao = buildNumberDao;
        _triggerService = triggerService;
        new Timer(true).schedule(this, new Date(), 10 * 1000);
    }

    @Override
    public void run() {
        try {
            List<BuildPlan> buildPlans = _buildPlanDao.getAll();
            for (BuildPlan buildPlan : buildPlans) {
                if (!buildPlan.getBuildNumbers().isEmpty()) {
                    BuildNumber buildNumber = _buildNumberDao.getHighestBuildNumber(buildPlan);
                    _triggerService.trigger(buildNumber);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
