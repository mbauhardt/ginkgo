package ginkgo.webapp.server;

public class MessageBoardEntry {

    private final String _message;
    private long _timeStamp;

    public MessageBoardEntry(String message) {
        _message = message;
        _timeStamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return _message;
    }

    public long getTimeStamp() {
        return _timeStamp;
    }
}
