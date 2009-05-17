package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao extends UniqueBaseNameDao<User> implements IUserDao {

    @Autowired
    public UserDao(PersistenceService persistenceService) {
        super(User.class, persistenceService);
    }

}
