package ginkgo.vcs.svn;

import ginkgo.api.IParameterValidatable;
import ginkgo.shared.ICommandable;
import ginkgo.shared.InvalidArgumentException;

import org.springframework.stereotype.Service;

@Service
public class SvnCheckoutCommand implements ICommandable, IParameterValidatable {

    private static final long serialVersionUID = 4399094581044836994L;

    private String _from;

    private String _to;

    public String buildCommand() {
        return "svn checkout " + _from + " " + _to;
    }

    public void parametrize(String... parameters) throws InvalidArgumentException {
        validate(parameters);
        _from = parameters[0];
        _to = parameters[1];
    }

    public void validate(String... parameters) throws InvalidArgumentException {
        if (parameters.length != 2) {
            throw new InvalidArgumentException("expect exactly 2 parameters.");
        }
    }

}
