package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.User;

public class UserCommand extends User {

    public void setId(Long id) {
        _id = id;
    }
}
