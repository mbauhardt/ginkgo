package ginkgo.vcs.svn;

import ginkgo.api.CommandPipe;
import ginkgo.api.IVcsable;
import ginkgo.shared.InvalidArgumentException;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Svn implements IVcsable {

    private static final long serialVersionUID = -8964376633239542853L;

    private final SvnCheckoutCommand _checkoutCommand;

    private final CommandPipe _commandPipe = new CommandPipe();

    @Autowired
    public Svn(SvnCheckoutCommand checkoutCommand) {
        _checkoutCommand = checkoutCommand;
    }

    public void branch() {
        // TODO Auto-generated method stub

    }

    public void checkout() throws IOException {
        //_commandPipe.execute(_checkoutCommand);
    }

    public void tag() {
        // TODO Auto-generated method stub

    }

    public void update() {
        // TODO Auto-generated method stub

    }

    public void parametrize(String... parameters) throws InvalidArgumentException {
        parametrizeCheckoutCommand(parameters[0]);
    }

    private void parametrizeCheckoutCommand(String trunk) throws InvalidArgumentException {
        _checkoutCommand.parametrize(trunk, "");
    }

    public String getName() {
        return "SVN";
    }

    public String getDescription() {
        return "Subversion Repository.";
    }

    public String[] getParameterNames() {
        return new String[] { "Trunk Url", "Branches Url", "Tags Url", "User Name", "Password" };

    }

    public IVcsable newInstance() {
        return new Svn(_checkoutCommand);
    }
}
