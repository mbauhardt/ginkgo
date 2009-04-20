package ginkgo.api;

import ginkgo.shared.BuildStatus;
import ginkgo.shared.ICommandLogger;

public interface IAgentServer extends ICommandLogger {

    void addStatus(String name, String message);

    void changeStatus(String agentName, Long commandId, BuildStatus status);

}
