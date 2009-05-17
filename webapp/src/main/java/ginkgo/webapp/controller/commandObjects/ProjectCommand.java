package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectCommand extends Project {

    private List<ConfigurationTupleCommand> _configurationTupleCommands = new ArrayList<ConfigurationTupleCommand>();

    private List<BuildPlanCommand> _buildPlanCommands = new ArrayList<BuildPlanCommand>();

    public void setId(Long id) {
        _id = id;
    }

    public List<ConfigurationTupleCommand> getConfigurationTupleCommands() {
        return _configurationTupleCommands;
    }

    public void setConfigurationTupleCommands(List<ConfigurationTupleCommand> configurationTupleCommands) {
        _configurationTupleCommands = configurationTupleCommands;
    }

    public void addConfigurationTupleCommand(ConfigurationTupleCommand command) {
        _configurationTupleCommands.add(command);
    }

    public List<BuildPlanCommand> getBuildPlanCommands() {
        return _buildPlanCommands;
    }

    public void setBuildPlanCommands(List<BuildPlanCommand> buildPlanCommands) {
        _buildPlanCommands = buildPlanCommands;
    }

    public void addBuildPlanCommand(BuildPlanCommand buildPlanCommand) {
        _buildPlanCommands.add(buildPlanCommand);
    }
}
