package ginkgo.webapp.persistence.model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Stage extends BaseName {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_fk")
    @OrderBy("_id")
    private Set<Step> _steps = new TreeSet<Step>();

    @ManyToOne
    @JoinColumn(name = "buildPlan_fk")
    private BuildPlan _buildPlan;

    public Stage() {
    }

    public Set<Step> getSteps() {
        return _steps;
    }

    public void setSteps(Set<Step> steps) {
        _steps = steps;
    }

    public void addStep(Step step) {
        _steps.add(step);
    }

    public BuildPlan getBuildPlan() {
        return _buildPlan;
    }

    public void setBuildPlan(BuildPlan buildPlan) {
        _buildPlan = buildPlan;
    }

}
