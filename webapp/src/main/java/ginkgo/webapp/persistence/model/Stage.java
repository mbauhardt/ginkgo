package ginkgo.webapp.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Stage extends BaseName {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_fk")
    private List<Step> _steps = new ArrayList<Step>();

    @ManyToOne
    @JoinColumn(name = "buildPlan_fk")
    private BuildPlan _buildPlan;

    public Stage() {
    }

    public List<Step> getSteps() {
        return _steps;
    }

    public void setSteps(List<Step> steps) {
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
