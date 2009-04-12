package ginkgo.api;

import ginkgo.shared.InvalidArgumentException;

import java.io.IOException;
import java.io.Serializable;

public interface IVcsable extends Serializable {

    String getName();

    String getDescription();

    void checkout() throws IOException;

    void update();

    void tag();

    void branch();

    void parametrize(String... parameters) throws InvalidArgumentException;

    String[] getParameterNames();

    IVcsable newInstance();

}
