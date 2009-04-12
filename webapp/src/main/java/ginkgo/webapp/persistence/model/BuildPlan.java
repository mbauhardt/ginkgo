package ginkgo.webapp.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BuildPlan extends BaseName {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildPlan_fk")
    private List<Stage> _stages = new ArrayList<Stage>();

    @ManyToOne
    @JoinColumn(name = "project_fk")
    private Project _project;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildPlan_fk")
    private List<BuildNumber> _buildNumbers = new ArrayList<BuildNumber>();

    public BuildPlan() {
    }

    public Project getProject() {
        return _project;
    }

    public void setProject(Project project) {
        _project = project;
    }

    public List<Stage> getStages() {
        return _stages;
    }

    public void setStages(List<Stage> stages) {
        _stages = stages;
    }

    public void addStage(Stage stage) {
        _stages.add(stage);
    }

    public List<BuildNumber> getBuildNumbers() {
        return _buildNumbers;
    }

    public void setBuildNumbers(List<BuildNumber> buildNumbers) {
        _buildNumbers = buildNumbers;
    }

    public void addBuildNumber(BuildNumber buildNumber) {
        _buildNumbers.add(buildNumber);
    }
}
