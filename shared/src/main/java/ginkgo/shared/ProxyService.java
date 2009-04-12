package ginkgo.shared;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyService<T> {

    @SuppressWarnings("unchecked")
    public T createProxy(Class<T> clazz, OutputStream outputStream) throws IOException {
        InvocationHandler handler = new RPCInvocationHandler(clazz, outputStream);
        Class[] classArray = new Class[] { clazz };
        Object instance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), classArray, handler);
        return (T) instance;
    }
}
