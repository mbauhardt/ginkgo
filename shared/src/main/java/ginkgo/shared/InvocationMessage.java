package ginkgo.shared;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;

public class InvocationMessage implements Externalizable {

    private Method _method;
    private Object[] _arguments = new Object[] {};
    private Class _clazz;

    public InvocationMessage() {
    }

    public InvocationMessage(Class clazz, Method method, Object[] arguments) {
        _clazz = clazz;
        _method = method;
        _arguments = arguments;
    }

    public void readExternal(ObjectInput input) throws IOException {
        try {
            String className = input.readUTF();
            String methodName = input.readUTF();
            int length = input.readInt();
            Class[] classArguments = new Class[length];
            _arguments = new Object[length];
            for (int i = 0; i < length; i++) {
                _arguments[i] = input.readObject();
                classArguments[i] = _arguments[i].getClass();
            }
            _clazz = Class.forName(className);
            _method = _clazz.getMethod(methodName, classArguments);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeUTF(_clazz.getName());
        output.writeUTF(_method.getName());

        output.writeInt(_arguments.length);
        for (Object object : _arguments) {
            output.writeObject(object);
        }
    }

    public Method getMethod() {
        return _method;
    }

    public Object[] getArguments() {
        return _arguments;
    }

    public Class getClazz() {
        return _clazz;
    }

}
