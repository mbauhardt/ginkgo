package ginkgo.webapp.persistence.dao;

import ginkgo.webapp.persistence.model.UniqueBaseName;

public interface IUniqueBaseNameDao<T extends UniqueBaseName> extends IBaseDao<T> {

    T getByName(String name) throws DaoException;

    boolean exists(String name);
}
