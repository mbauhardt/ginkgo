package ginkgo.agent;

import ginkgo.api.IAgentServer;
import ginkgo.api.IBuildAgent;
import ginkgo.shared.Command;
import ginkgo.shared.MessageConsumer;
import ginkgo.shared.ProxyService;

import java.io.File;
import java.io.IOException;
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
    private final File _workingDirectory;

    public BuildAgent(String name, String ip, int port, File workingDirectory) {
        _name = name;
        _ip = ip;
        _port = port;
        _workingDirectory = workingDirectory;
        _proxyService = new ProxyService<IAgentServer>();
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

    public void execute(String buildPlanName, Long commandId, String command) {
        HeartBeat.setMessage(command);
        Command commandObject = new Command(commandId, _name, _agentServer);
        try {
            LOG.info("execute: " + command);
            new File(_workingDirectory, buildPlanName).mkdirs();
            boolean execute = commandObject.execute(command, new String[] {},
                    new File(_workingDirectory, buildPlanName));
            _agentServer.executeStatus(_name, commandId, execute);
        } catch (IOException e) {
            LOG.error("error executing command: " + command, e);
            _agentServer.executeStatus(_name, commandId, false);
        }
    }

    public static void main(String[] args) throws Exception {
        BuildAgent buildAgent = new BuildAgent("mbauhardt", "127.0.0.1", 9000, new File("/tmp/ginkgo"));
        buildAgent.startAgent();
    }

}
