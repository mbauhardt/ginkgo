package ginkgo.webapp.controller;

import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.IBuildCommandDao;
import ginkgo.webapp.server.AgentRepository;
import ginkgo.webapp.server.AgentRepositoryEntry;
import ginkgo.webapp.server.AgentServer;
import ginkgo.webapp.server.MessageBoard;
import ginkgo.webapp.server.MessageBoardEntry;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user/buildAgents.html")
public class BuildAgentController {

    private AgentServer _agentServer;
    private final AgentRepository _agentRepository;
    private final MessageBoard _messageBoard;

    @Autowired
    public BuildAgentController(AgentRepository repo, MessageBoard messageBoard, PersistenceService persistenceService,
            IBuildCommandDao buildCommandDao) throws IOException {
        _agentRepository = repo;
        _messageBoard = messageBoard;
        _agentServer = new AgentServer(repo, messageBoard, persistenceService, buildCommandDao);
        _agentServer.startServer();
    }

    @ModelAttribute("agents")
    public Map<String, MessageBoardEntry> showBuildAgents() {
        Map<String, MessageBoardEntry> map = new HashMap<String, MessageBoardEntry>();
        List<AgentRepositoryEntry> entries = _agentRepository.getEntries();
        for (AgentRepositoryEntry repositoryEntry : entries) {
            String agentName = repositoryEntry.getAgentName();
            MessageBoardEntry message = _messageBoard.getMessage(agentName);
            map.put(agentName, message);
        }
        return map;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listAgents() {
        return "user/buildAgents";
    }

}
