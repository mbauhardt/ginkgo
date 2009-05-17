package ginkgo.api;

import ginkgo.shared.InvalidArgumentException;

public interface IVcsable {

    String getName();

    String getDescription();

    String getCheckoutCommand();

    String getUpdateCommand();

    void getTagCommand();

    void getBranchCommand();

    void parametrize(String... parameters) throws InvalidArgumentException;

    String[] getParameterNames();

}
