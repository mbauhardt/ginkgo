package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.model.Base;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.criterion.Order;

public abstract class Dao<T extends Base> implements IDao<T> {

    protected final Class<T> _clazz;

    protected final PersistenceService _persistenceService;

    public Dao(Class<T> clazz, PersistenceService persistenceService) {
        _clazz = clazz;
        _persistenceService = persistenceService;
    }

    public void makePersistent(T t) throws DaoException {
        _persistenceService.persist(t);
    }

    public void makeTransient(T t) {
        _persistenceService.remove(t);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() throws DaoException {
        Query query = _persistenceService.createQuery("select t from " + _clazz.getName() + " as t");
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll(Order order, int maxResults) throws DaoException {
        Query query = _persistenceService.createQuery("select t from " + _clazz.getName() + " as t order by " + order);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

}
