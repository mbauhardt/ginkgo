package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.model.BaseName;

public interface IBaseNameDao<T extends BaseName> extends IBaseDao<T> {

    T getByName(String name) throws DaoException;

    boolean exists(String name);
}
