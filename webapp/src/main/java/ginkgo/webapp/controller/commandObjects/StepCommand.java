package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.Step;

public class StepCommand extends Step {

    public void setId(Long id) {
        _id = id;
    }
}
