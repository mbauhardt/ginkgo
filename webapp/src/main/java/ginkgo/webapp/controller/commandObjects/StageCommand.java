package ginkgo.webapp.controller.commandObjects;

import java.util.ArrayList;
import java.util.List;

import ginkgo.webapp.persistence.model.Stage;

public class StageCommand extends Stage {

    private List<StepCommand> _stepCommands = new ArrayList<StepCommand>();

    public void setId(Long id) {
        _id = id;
    }

    public List<StepCommand> getStepCommands() {
        return _stepCommands;
    }

    public void setStepCommands(List<StepCommand> stepCommands) {
        _stepCommands = stepCommands;
    }

    public void addStepCommand(StepCommand stepCommand) {
        _stepCommands.add(stepCommand);
    }

}
