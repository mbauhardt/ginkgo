package ginkgo.webapp.server;

import ginkgo.api.IAgentServer;
import ginkgo.api.IBuildAgent;
import ginkgo.shared.BuildStatus;
import ginkgo.shared.MessageConsumer;
import ginkgo.shared.ProxyService;
import ginkgo.webapp.persistence.PersistenceService;
import ginkgo.webapp.persistence.dao.DaoException;
import ginkgo.webapp.persistence.dao.IBuildCommandDao;
import ginkgo.webapp.persistence.model.BuildCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgentServer implements IAgentServer, Runnable, IRegistration {

    private ServerSocket _serverSocket;
    private final AgentRepository _repository;
    private ProxyService<IBuildAgent> _proxyService;
    private final MessageBoard _messageBoard;
    private static final Logger LOG = LoggerFactory.getLogger(AgentServer.class);
    private final PersistenceService _persistenceService;
    private final IBuildCommandDao _buildCommandDao;

    public AgentServer(AgentRepository repository, MessageBoard messageBoard, PersistenceService persistenceService,
            IBuildCommandDao buildCommandDao) {
        _repository = repository;
        _messageBoard = messageBoard;
        _persistenceService = persistenceService;
        _buildCommandDao = buildCommandDao;
        _proxyService = new ProxyService<IBuildAgent>();
    }

    public void stopServer() throws IOException {
        _serverSocket.close();
        _repository.stopAllBuildAgents();
    }

    public void startServer() throws IOException {
        new Thread(this).start();
    }

    public void addStatus(String name, String message) {
        _messageBoard.pinMessage(name, new MessageBoardEntry(message));
    }

    public void run() {
        try {
            _serverSocket = new ServerSocket(9000);
            Socket socket = null;
            while ((socket = _serverSocket.accept()) != null) {
                LOG.info("new build agent will be connect: " + socket);
                new RegistrationThread(socket, this).start();
            }
        } catch (Exception e) {
            LOG.error("error while registration", e);
        }

    }

    public void register(String agentName, Socket socket) throws IOException {
        LOG.info("register agent: " + agentName);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(1);
        new MessageConsumer<AgentServer>(inputStream, this, new MessageConsumerListener(agentName, _repository))
                .start();
        IBuildAgent buildAgent = _proxyService.createProxy(IBuildAgent.class, outputStream);
        _repository.add(new AgentRepositoryEntry(agentName, buildAgent, socket));
    }

    public boolean isRegistered(String agentName) {
        return _repository.contains(agentName);
    }

    public void changeStatus(String agentName, Long commandId, BuildStatus status) {
        _persistenceService.beginTransaction();
        try {
            BuildCommand buildCommand = _buildCommandDao.getById(commandId);
            Map<String, BuildStatus> buildAgentStatus = buildCommand.getBuildAgentStatus();
            buildAgentStatus.put(agentName, status);
        } catch (DaoException e) {
            LOG.error("error while update status on buildAgent", e);
        }
        _persistenceService.commitTransaction();
    }

    public void log(String agentName, Long commandId, String line) {

    }

}
