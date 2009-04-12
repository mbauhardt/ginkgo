package ginkgo.agent;

import ginkgo.api.IAgentServer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeat {

    private static final Logger LOG = LoggerFactory.getLogger(HeartBeat.class);
    private String _message = "PENDING";
    private Timer _timer;
    private final IAgentServer _agentServer;
    private final String _name;

    public class SendStatusTask extends TimerTask {
        @Override
        public void run() {
            _agentServer.addStatus(_name, _message);
        }
    }

    public HeartBeat(String name, IAgentServer agentServer) {
        _name = name;
        _agentServer = agentServer;
        _timer = new Timer(true);
    }

    public void start() {
        LOG.info("Start hearbeats.");
        TimerTask task = new SendStatusTask();
        _timer.schedule(task, new Date(), 3 * 1000);
    }

}
