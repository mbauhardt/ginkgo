package ginkgo.webapp.persistence.dao;

import java.util.List;

import ginkgo.webapp.persistence.model.BaseName;

public interface IBaseNameDao<T extends BaseName> extends IBaseDao<T> {

    List<T> getByName(String name) throws DaoException;

    boolean exists(String name);
}
