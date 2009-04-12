package ginkgo.api;

import ginkgo.shared.CommandQueue;

public interface IBuildAgent {

    void execute(CommandQueue<String> commandQueue);

    void stopAgent() throws Exception;

    void execute(String command);

}
