package ginkgo.shared;

import java.io.Serializable;

public interface ICommandable extends Serializable {

    void parametrize(String... parameters) throws InvalidArgumentException;

    String buildCommand();
}
