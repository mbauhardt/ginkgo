package ginkgo.vcs.svn;

import ginkgo.api.VcsPlugin;
import ginkgo.shared.InvalidArgumentException;

import org.springframework.stereotype.Service;

@Service
public class Svn extends VcsPlugin {

    private String _checkoutUrl;
    private String _branchestUrl;
    private String _tagsUrl;
    private String _userName;
    private String _password;

    public String getName() {
        return "SVN";
    }

    public String getDescription() {
        return "Subversion Repository.";
    }

    public String[] getParameterNames() {
        return new String[] { "Checkout Url", "Branches Url", "Tags Url", "User Name", "Password" };

    }

    public void getBranchCommand() {
        // TODO Auto-generated method stub

    }

    public String getCheckoutCommand() {
        return "svn checkout " + _checkoutUrl;
    }

    public void getTagCommand() {
        // TODO Auto-generated method stub

    }

    public String getUpdateCommand() {
        // TODO Auto-generated method stub
        return null;
    }

    public void parametrize(String... parameters) throws InvalidArgumentException {
        _checkoutUrl = parameters[0];
        _branchestUrl = parameters[1];
        _tagsUrl = parameters[2];
        _userName = parameters[3];
        _password = parameters[4];
    }

}
