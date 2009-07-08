package ginkgo.webapp.persistence.dao;

import java.util.List;

import org.hibernate.criterion.Order;

public interface IDao<T> {

    void makePersistent(T t) throws DaoException;

    void makeTransient(T t);

    void flush();

    List<T> getAll() throws DaoException;

    List<T> getAll(Order order, int maxResults) throws DaoException;

}
