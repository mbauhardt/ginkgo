package ginkgo.webapp.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationThread.class);
    private final Socket _socket;
    private final IRegistration _registration;

    public RegistrationThread(Socket socket, IRegistration registration) {
        _socket = socket;
        _registration = registration;
    }

    @Override
    public void run() {
        try {
            LOG.info("Registration starts...");
            InputStream inputStream = _socket.getInputStream();
            LOG.info("Read agent name.");
            int length = inputStream.read();
            byte[] bytes = new byte[length];
            inputStream.read(bytes, 0, length);
            // TODO check if name exists
            String agentName = new String(bytes);
            LOG.info("check if agent [" + agentName + "] name exists.");
            if (!_registration.isRegistered(agentName)) {
                LOG.info("register agent: " + agentName);
                _registration.register(agentName, _socket);
            } else {
                LOG.warn("agent already registered: " + agentName);
                _socket.getOutputStream().write(-1);
                _socket.close();
            }

        } catch (IOException e) {
            LOG.error("registration fails.", e);
        }

    }
}
