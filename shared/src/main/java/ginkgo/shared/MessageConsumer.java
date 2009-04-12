package ginkgo.shared;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageConsumer<T> extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class);
    private ObjectInputStream _inputStream;
    private final T _t;
    private final IThreadStopListener _listener;

    public MessageConsumer(InputStream inputStream, T t, IThreadStopListener listener) throws IOException {
        _t = t;
        _listener = listener;
        _inputStream = new ObjectInputStream(inputStream);
    }

    @Override
    public void run() {
        Object object = null;
        try {
            while ((object = _inputStream.readObject()) != null) {
                InvocationMessage message = (InvocationMessage) object;
                Object[] arguments = message.getArguments();
                Method method = message.getMethod();
                method.invoke(_t, arguments);
            }
        } catch (Throwable t) {
            LOG.warn("Error by consume messages. Maybe socket close?", t);
        }
        _listener.handleStop();
    }
}
