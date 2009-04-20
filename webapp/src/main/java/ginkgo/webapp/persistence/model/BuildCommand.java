package ginkgo.webapp.persistence.model;

import ginkgo.shared.BuildStatus;
import ginkgo.webapp.persistence.service.ITriggerable;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class BuildCommand extends Base implements ITriggerable {

    private String _command;

    @CollectionOfElements(fetch = FetchType.EAGER)
    private Map<String, BuildStatus> _buildAgentStatus = new HashMap<String, BuildStatus>();

    @OneToOne
    private BuildCommand _nextBuildCommand;

    public String getCommand() {
        return _command;
    }

    public void setCommand(String command) {
        _command = command;
    }

    public Map<String, BuildStatus> getBuildAgentStatus() {
        return _buildAgentStatus;
    }

    public void setBuildAgentStatus(Map<String, BuildStatus> buildAgentStatus) {
        _buildAgentStatus = buildAgentStatus;
    }

    public void addStatus(String agentName, BuildStatus status) {
        _buildAgentStatus.put(agentName, status);
    }

    public BuildCommand getNextBuildCommand() {
        return _nextBuildCommand;
    }

    public void setNextBuild(BuildCommand nextBuild) {
        _nextBuildCommand = nextBuild;
    }

}
