package ginkgo.shared;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RPCInvocationHandler implements InvocationHandler {

    private final Class<?> _clazz;
    private ObjectOutputStream _outputStream;

    public RPCInvocationHandler(Class<?> clazz, OutputStream outputStream) throws IOException {
        _clazz = clazz;
        _outputStream = new ObjectOutputStream(outputStream);
    }

    public Object invoke(Object object, Method method, Object[] arguments) throws Throwable {
        arguments = arguments == null ? new Object[]{} : arguments;;
        InvocationMessage invocationMessage = new InvocationMessage(_clazz, method, arguments);
        _outputStream.writeObject(invocationMessage);
        return null;
    }
}
