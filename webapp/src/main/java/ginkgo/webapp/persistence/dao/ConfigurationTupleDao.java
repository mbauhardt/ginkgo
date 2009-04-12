package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.ConfigurationTuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationTupleDao extends BaseDao<ConfigurationTuple> implements IConfigurationTupleDao {

    @Autowired
    public ConfigurationTupleDao(PersistenceService persistenceService) {
        super(ConfigurationTuple.class, persistenceService);
    }

}
