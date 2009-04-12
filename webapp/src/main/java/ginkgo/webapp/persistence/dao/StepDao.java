package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.Step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepDao extends BaseNameDao<Step> implements IStepDao {

    @Autowired
    public StepDao(PersistenceService persistenceService) {
        super(Step.class, persistenceService);
    }
}
