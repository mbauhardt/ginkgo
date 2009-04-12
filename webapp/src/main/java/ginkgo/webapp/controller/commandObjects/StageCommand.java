package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.Stage;

public class StageCommand extends Stage {

    public void setId(Long id) {
        _id = id;
    }
}
