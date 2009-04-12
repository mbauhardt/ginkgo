package ginkgo.webapp.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Project extends BaseName {

    private String _vcs;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_fk")
    private List<ConfigurationTuple> _configurationTuples = new ArrayList<ConfigurationTuple>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_fk")
    private List<BuildPlan> _buildPlans;

    public Project() {
    }

    public String getVcs() {
        return _vcs;
    }

    public void setVcs(String vcs) {
        _vcs = vcs;
    }

    public List<ConfigurationTuple> getConfigurationTuples() {
        return _configurationTuples;
    }

    public void setConfigurationTuples(List<ConfigurationTuple> configurationTuples) {
        _configurationTuples = configurationTuples;
    }

    public void addConfigurationTuple(ConfigurationTuple configuration) {
        _configurationTuples.add(configuration);
    }

    public List<BuildPlan> getBuildPlans() {
        return _buildPlans;
    }

    public void setBuildPlans(List<BuildPlan> buildPlans) {
        _buildPlans = buildPlans;
    }

    public void addBuildPlan(BuildPlan buildPlan) {
        _buildPlans.add(buildPlan);
    }
}
