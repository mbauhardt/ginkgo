package ginkgo.shared;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class CommandQueue<T> implements Serializable {

    private static final long serialVersionUID = 7833041487877729300L;

    private Queue<T> _queue = new LinkedList<T>();

    public T pop() {
        return _queue.poll();
    }

    public void push(T t) {
        _queue.add(t);
    }

}
