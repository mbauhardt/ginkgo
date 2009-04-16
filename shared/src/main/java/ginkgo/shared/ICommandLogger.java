package ginkgo.shared;

public interface ICommandLogger {

    void log(String agentName, Long commandId, String line);

}
