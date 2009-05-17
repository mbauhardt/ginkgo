package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.BuildPlan;

import java.util.ArrayList;
import java.util.List;

public class BuildPlanCommand extends BuildPlan {

    private List<StageCommand> _stageCommands = new ArrayList<StageCommand>();

    private ProjectCommand _projectCommand;

    public void setId(Long id) {
        _id = id;
    }

    public List<StageCommand> getStageCommands() {
        return _stageCommands;
    }

    public void setStageCommands(List<StageCommand> stageCommands) {
        _stageCommands = stageCommands;
    }

    public void addStageCommand(StageCommand stageCommand) {
        _stageCommands.add(stageCommand);
    }

    public ProjectCommand getProjectCommand() {
        return _projectCommand;
    }

    public void setProjectCommand(ProjectCommand projectCommand) {
        _projectCommand = projectCommand;
    }

}
