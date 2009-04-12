package ginkgo.webapp.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class MessageBoard {

    private Map<String, MessageBoardEntry> _messages = new HashMap<String, MessageBoardEntry>();

    public void pinMessage(String agentName, MessageBoardEntry entry) {
        _messages.put(agentName, entry);
    }

    public MessageBoardEntry getMessage(String agentName) {
        return _messages.get(agentName);
    }

}
