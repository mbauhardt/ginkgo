package ginkgo.webapp.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Step extends Base {

    private String _command;

    @ManyToOne
    @JoinColumn(name = "stage_fk", nullable = false)
    private Stage _stage;

    public String getCommand() {
        return _command;
    }

    public void setCommand(String command) {
        _command = command;
    }

    public Stage getStage() {
        return _stage;
    }

    public void setStage(Stage stage) {
        _stage = stage;
    }

}
