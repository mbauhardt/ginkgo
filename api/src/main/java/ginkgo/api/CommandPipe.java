package ginkgo.api;

import ginkgo.shared.Command;
import ginkgo.shared.ICommandable;

import java.io.IOException;
import java.io.Serializable;

public class CommandPipe implements Serializable {

    private static final long serialVersionUID = 3724158541004379377L;

    private Command _command = new Command();

    public void execute(ICommandable... commandables) throws IOException {
        for (ICommandable commandable : commandables) {
            String command = commandable.buildCommand();
            _command.execute(command);
        }
    }

}
