package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectDao extends BaseNameDao<Project> implements IProjectDao {

    @Autowired
    public ProjectDao(PersistenceService persistenceService) {
        super(Project.class, persistenceService);
    }

}
