package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.BuildPlan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildPlanDao extends BaseNameDao<BuildPlan> implements IBuildPlanDao {

    @Autowired
    public BuildPlanDao(PersistenceService persistenceService) {
        super(BuildPlan.class, persistenceService);
    }

}
