package ginkgo.webapp.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ConfigurationTuple extends Base {

    private String _tupleKey;

    private String _tupleValue;

    @ManyToOne
    @JoinColumn(name = "project_fk")
    private Project _project;

    public String getTupleKey() {
        return _tupleKey;
    }

    public void setTupleKey(String tupleKey) {
        _tupleKey = tupleKey;
    }

    public String getTupleValue() {
        return _tupleValue;
    }

    public void setTupleValue(String tupleValue) {
        _tupleValue = tupleValue;
    }

    @Override
    public String toString() {
        return _tupleKey + ":" + _tupleValue;
    }

    public Project getProject() {
        return _project;
    }

    public void setProject(Project project) {
        _project = project;
    }

}
