package ginkgo.webapp.controller.editor;

import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBaseDao;
import ginkgo.webapp.persistence.model.Base;

import java.beans.PropertyEditorSupport;


public class BaseEditor extends PropertyEditorSupport {

    private final IBaseDao<? extends Base> _dao;

    public BaseEditor(IBaseDao<? extends Base> dao) {
        _dao = dao;
    }

    public String getAsText() {
        Object object = getValue();
        Base base = (Base) object;
        return base != null && base.getId() != null ? base.getId().toString() : "";
    }

    public void setAsText(String text) {
        Long id = -1L;
        try {
            id = Long.parseLong(text);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (id != -1) {
            try {
                Base base = _dao.getById(id);
                setValue(base);
            } catch (DaoException e) {
                throw new IllegalArgumentException(e.getMessage());
            }

        }
    }

}
