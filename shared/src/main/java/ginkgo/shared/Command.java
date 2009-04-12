package ginkgo.shared;

import java.io.IOException;
import java.io.Serializable;

public class Command implements Serializable {

    private static final long serialVersionUID = -7661470749347925683L;

    public boolean execute(String command) throws IOException {
        int exitValue = 0;
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);
        try {
            exitValue = process.waitFor();
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        }
        return exitValue != 0 ? false : true;
    }
}
