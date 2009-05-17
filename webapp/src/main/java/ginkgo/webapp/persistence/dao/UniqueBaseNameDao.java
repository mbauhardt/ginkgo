package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.UniqueBaseName;

import java.util.List;

import javax.persistence.Query;

public abstract class UniqueBaseNameDao<T extends UniqueBaseName> extends BaseDao<T> implements IUniqueBaseNameDao<T> {

    public UniqueBaseNameDao(Class<T> clazz, PersistenceService persistenceService) {
        super(clazz, persistenceService);
    }

    @SuppressWarnings("unchecked")
    public boolean exists(String name) {
        Query createQuery = _persistenceService.createQuery("select t from " + _clazz.getSimpleName()
                + " as t where t._name = ?1");
        createQuery.setParameter(1, name);
        Object result = createQuery.getResultList();
        return !((List<T>) result).isEmpty();
    }

    @SuppressWarnings("unchecked")
    public T getByName(String name) throws DaoException {
        Query createQuery = _persistenceService.createQuery("select t from " + _clazz.getSimpleName()
                + " as t where t._name = ?1");
        createQuery.setParameter(1, name);
        Object result = createQuery.getSingleResult();
        return (T) result;
    }
}
