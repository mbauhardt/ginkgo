package ginkgo.webapp.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

@Service
public class PersistenceService {

    private EntityManagerFactory _entityManagerFactory;

    private final ThreadLocal<EntityManager> _threadLocal = new ThreadLocal<EntityManager>() {
        @Override
        protected EntityManager initialValue() {
            return _entityManagerFactory.createEntityManager();
        }
    };

    public PersistenceService() {
        _entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
    }

    public void beginTransaction() {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
    }

    private EntityManager getEntityManager() {
        EntityManager entityManager = _threadLocal.get();
        if (entityManager == null) {
            entityManager = _entityManagerFactory.createEntityManager();
            _threadLocal.set(entityManager);
        }
        return entityManager;
    }

    public void commitTransaction() {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
    }

    public void rollbackTransaction() {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.rollback();
        }
    }

    public void close() {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            _threadLocal.set(null);
            entityManager.close();
        }
    }

    public void persist(Object object) {
        EntityManager entityManager = getEntityManager();
        entityManager.persist(object);
    }

    public void remove(Object object) {
        EntityManager entityManager = getEntityManager();
        entityManager.remove(object);
    }

    public Object getById(Class<?> clazz, Serializable id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(clazz, id);
    }

    public Query createQuery(String query) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery(query);
    }

}
