package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.Base;

import java.io.Serializable;


public abstract class BaseDao<T extends Base> extends Dao<T> implements IBaseDao<T> {

    public BaseDao(Class<T> clazz, PersistenceService persistenceService) {
        super(clazz, persistenceService);
    }

    @SuppressWarnings("unchecked")
    public T getById(Serializable id) throws DaoException {
        return (T) _persistenceService.getById(_clazz, id);
    }
}
