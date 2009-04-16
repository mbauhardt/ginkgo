package ginkgo.api;

import ginkgo.shared.ICommandLogger;

public interface IAgentServer extends ICommandLogger {

    void addStatus(String name, String message);

    void executeStatus(String agentName, Long commandId, Boolean status);

}
