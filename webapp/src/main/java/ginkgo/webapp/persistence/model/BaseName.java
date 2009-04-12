package ginkgo.webapp.persistence.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseName extends Base {

    @Column(unique = true)
    private String _name;

    public BaseName() {
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

}
