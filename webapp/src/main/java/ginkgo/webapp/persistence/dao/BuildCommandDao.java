package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.BuildCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildCommandDao extends BaseDao<BuildCommand> implements IBuildCommandDao {

    @Autowired
    public BuildCommandDao(PersistenceService persistenceService) {
        super(BuildCommand.class, persistenceService);
    }

}
