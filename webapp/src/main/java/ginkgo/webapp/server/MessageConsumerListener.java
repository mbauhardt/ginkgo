package ginkgo.webapp.server;

import ginkgo.shared.IThreadStopListener;

public class MessageConsumerListener implements IThreadStopListener {

    private final AgentRepository _repository;
    private final String _agentName;

    public MessageConsumerListener(String agentName, AgentRepository repository) {
        _agentName = agentName;
        _repository = repository;
    }

    public void handleStop() {
        _repository.remove(_agentName);
    }

}
