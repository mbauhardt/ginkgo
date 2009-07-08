package ginkgo.webapp.persistence.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class BuildPlan extends BaseName {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildPlan_fk")
    @OrderBy("_id")
    private Set<Stage> _stages = new TreeSet<Stage>();

    @ManyToOne
    @JoinColumn(name = "project_fk", nullable = false)
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

    public Set<Stage> getStages() {
        return _stages;
    }

    public void setStages(Set<Stage> stages) {
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
