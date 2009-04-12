package ginkgo.webapp.controller.commandObjects;

import ginkgo.webapp.persistence.model.ConfigurationTuple;

public class ConfigurationTupleCommand extends ConfigurationTuple {

    public ConfigurationTupleCommand() {
    }

    public ConfigurationTupleCommand(ConfigurationTuple tuple) {
        setTupleKey(tuple.getTupleKey());
        setTupleValue(tuple.getTupleValue());
    }
}
