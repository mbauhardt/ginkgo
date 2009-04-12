package ginkgo.webapp.server;

import java.io.IOException;
import java.net.Socket;

public interface IRegistration {

    void register(String agentName, Socket socket) throws IOException;

    boolean isRegistered(String agentName);
}
