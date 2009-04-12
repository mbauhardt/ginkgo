package ginkgo.webapp.server;

import ginkgo.api.IBuildAgent;

import java.net.Socket;

public class AgentRepositoryEntry {

    private final IBuildAgent _buildAgent;
    private final Socket _socket;
    private final String _agentName;

    public AgentRepositoryEntry(String agentName, IBuildAgent buildAgent, Socket socket) {
        _agentName = agentName;
        _buildAgent = buildAgent;
        _socket = socket;
    }

    public IBuildAgent getBuildAgent() {
        return _buildAgent;
    }

    public Socket getSocket() {
        return _socket;
    }

    public String getAgentName() {
        return _agentName;
    }

}
