package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StageDao extends BaseNameDao<Stage> implements IStageDao {

    @Autowired
    public StageDao(PersistenceService persistenceService) {
        super(Stage.class, persistenceService);
    }
}
