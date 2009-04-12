package ginkgo.agent;

import ginkgo.api.IAgentServer;
import ginkgo.api.IBuildAgent;
import ginkgo.shared.CommandQueue;
import ginkgo.shared.MessageConsumer;
import ginkgo.shared.ProxyService;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuildAgent implements IBuildAgent {

    private static final Logger LOG = LoggerFactory.getLogger(BuildAgent.class);
    private final String _ip;
    private final int _port;
    private Socket _socket;
    private ProxyService<IAgentServer> _proxyService;
    private IAgentServer _agentServer;
    private final String _name;

    public BuildAgent(String name, String ip, int port) {
        _name = name;
        _ip = ip;
        _port = port;
        _proxyService = new ProxyService<IAgentServer>();
    }

    public void execute(CommandQueue<String> commandQueue) {
        String command = null;
        while ((command = commandQueue.pop()) != null) {
            System.out.println("BuildAgent.execute() " + command);
            _agentServer.addStatus(_name, "execute: " + command);
        }
    }

    public void startAgent() throws Exception {
        _socket = new Socket(_ip, _port);
        OutputStream outputStream = _socket.getOutputStream();
        InputStream inputStream = _socket.getInputStream();

        LOG.info("try to register with name: " + _name);
        byte[] bytes = _name.getBytes();
        outputStream.write(bytes.length);
        outputStream.write(bytes, 0, bytes.length);
        int status = inputStream.read();
        if (status == 1) {
            LOG.info("Registration success.");
            _agentServer = _proxyService.createProxy(IAgentServer.class, outputStream);
            new HeartBeat(_name, _agentServer).start();
            MessageConsumer<IBuildAgent> messageConsumer = new MessageConsumer<IBuildAgent>(inputStream, this,
                    new MessageConsumeStopListener());
            messageConsumer.start();
        } else {
            LOG.warn("Registration fails.");
        }

    }

    public void stopAgent() throws Exception {
        _socket.close();
    }

    public static void main(String[] args) throws Exception {
        BuildAgent buildAgent = new BuildAgent("mbauhardt", "127.0.0.1", 9000);
        buildAgent.startAgent();
    }

    public void execute(String command) {
        System.out.println("BuildAgent.execute() " + command);
    }

}
