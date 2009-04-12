package ginkgo.api;

import ginkgo.shared.InvalidArgumentException;

public interface IParameterValidatable {

    void validate(String... parameters) throws InvalidArgumentException;

}
