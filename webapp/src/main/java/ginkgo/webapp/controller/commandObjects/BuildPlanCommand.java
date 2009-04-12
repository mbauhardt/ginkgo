package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.BuildPlan;

public class BuildPlanCommand extends BuildPlan {

    public void setId(Long id) {
        _id = id;
    }
    
}
