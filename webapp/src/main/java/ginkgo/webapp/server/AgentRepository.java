package ginkgo.webapp.server;

import ginkgo.api.IBuildAgent;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AgentRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AgentRepository.class);

    private Map<String, AgentRepositoryEntry> _entries = new HashMap<String, AgentRepositoryEntry>();

    public synchronized void stopAllBuildAgents() {

        Collection<AgentRepositoryEntry> values = _entries.values();
        for (AgentRepositoryEntry entry : values) {
            try {
                IBuildAgent buildAgent = entry.getBuildAgent();
                Socket socket = entry.getSocket();
                LOG.info("stop agent on socket: " + socket);
                buildAgent.stopAgent();
                LOG.info("close socket: " + socket);
                socket.close();
            } catch (Exception e) {
                LOG.warn("error by stopping build agent", e);
            }
        }
        _entries.clear();
    }

    public synchronized void add(AgentRepositoryEntry repositoryEntry) {
        _entries.put(repositoryEntry.getAgentName(), repositoryEntry);
    }

    public List<AgentRepositoryEntry> getEntries() {
        return new ArrayList<AgentRepositoryEntry>(_entries.values());
    }

    public void remove(String agentName) {
        _entries.remove(agentName);
    }

    public boolean contains(String agentName) {
        return _entries.containsKey(agentName);
    }
}
