package ginkgo.api;

public interface IBuildAgent {

    void stopAgent() throws Exception;

    void execute(String projectName, Long commandId, String command);

}
