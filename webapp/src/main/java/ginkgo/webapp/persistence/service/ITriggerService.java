package ginkgo.webapp.persistence.service;

import ginkgo.webapp.persistence.dao.DaoException;

public interface ITriggerService<T extends ITriggerable> {

    boolean trigger(T t) throws DaoException;
}
