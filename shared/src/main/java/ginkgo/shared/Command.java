package ginkgo.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;

public class Command implements Serializable {

    private static final long serialVersionUID = -7661470749347925683L;
    private final String _agentName;
    private final ICommandLogger _commandLogger;
    private final Long _commandId;

    private static class StreamReader implements Runnable {

        private boolean _stop = false;
        private BufferedReader _bufferedReader;
        private final ICommandLogger _commandLogger;

        public StreamReader(InputStream inputStream, ICommandLogger commandLogger) {
            _commandLogger = commandLogger;
            _bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public void run() {
            try {
                String line = null;
                while ((line = _bufferedReader.readLine()) != null) {
                    _commandLogger.log("foo", 1L, line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean isStop() {
            return _stop;
        }

        public void setStop(boolean stop) {
            _stop = stop;
        }

    }

    public Command(Long commandId, String agentName, ICommandLogger commandLogger) {
        _commandId = commandId;
        _agentName = agentName;
        _commandLogger = commandLogger;
    }

    public boolean execute(String command, String[] parameters, File directory) throws IOException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        int exitValue = 0;
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command, parameters, directory);
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();
        StreamReader inputStreamReader = new StreamReader(inputStream, _commandLogger);
        StreamReader errorStreamReader = new StreamReader(errorStream, _commandLogger);
        new Thread(inputStreamReader).start();
        new Thread(errorStreamReader).start();
        try {
            exitValue = process.waitFor();
            inputStreamReader.setStop(true);
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }
        return exitValue != 0 ? false : true;
    }

}
