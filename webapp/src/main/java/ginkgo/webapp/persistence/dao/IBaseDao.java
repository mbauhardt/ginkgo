package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.model.Base;

import java.io.Serializable;


public interface IBaseDao<T extends Base> extends IDao<T> {

    T getById(Serializable id) throws DaoException;
}
