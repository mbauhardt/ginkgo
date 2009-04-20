package ginkgo.webapp.persistence.model;

import ginkgo.shared.BuildStatus;
import ginkgo.webapp.persistence.service.ITriggerable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BuildNumber extends Base implements ITriggerable {

    private int _buildNumber;

    @OneToOne
    private BuildCommand _buildCommand;

    @ManyToOne
    @JoinColumn(name = "buildPlan_fk")
    private BuildPlan _buildPlan;

    transient private BuildStatus _buildStatus = BuildStatus.NOT_RUNNING;

    public int getBuildNumber() {
        return _buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        _buildNumber = buildNumber;
    }

    public BuildCommand getBuildCommand() {
        return _buildCommand;
    }

    public void setBuildCommand(BuildCommand buildCommand) {
        _buildCommand = buildCommand;
    }

    public BuildPlan getBuildPlan() {
        return _buildPlan;
    }

    public void setBuildPlan(BuildPlan buildPlan) {
        _buildPlan = buildPlan;
    }

    public BuildStatus getBuildStatus() {
        return _buildStatus;
    }

    public void setBuildStatus(BuildStatus buildStatus) {
        _buildStatus = buildStatus;
    }

}
