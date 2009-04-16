package ginkgo.webapp.persistence.model;

import ginkgo.webapp.persistence.service.ITriggerable;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class BuildCommand extends Base implements ITriggerable {

    public static enum Status {
        NOT_RUNNING, RUNNING, SUCCESS, FAILURE
    }

    private String _command;

    @CollectionOfElements(fetch = FetchType.EAGER)
    private Map<String, Status> _buildAgentStatus = new HashMap<String, Status>();

    @OneToOne
    private BuildCommand _nextBuild;

    public String getCommand() {
        return _command;
    }

    public void setCommand(String command) {
        _command = command;
    }

    public Map<String, Status> getBuildAgentStatus() {
        return _buildAgentStatus;
    }

    public void setBuildAgentStatus(Map<String, Status> buildAgentStatus) {
        _buildAgentStatus = buildAgentStatus;
    }

    public void addStatus(String agentName, Status status) {
        _buildAgentStatus.put(agentName, status);
    }

    public BuildCommand getNextBuild() {
        return _nextBuild;
    }

    public void setNextBuild(BuildCommand nextBuild) {
        _nextBuild = nextBuild;
    }

}
