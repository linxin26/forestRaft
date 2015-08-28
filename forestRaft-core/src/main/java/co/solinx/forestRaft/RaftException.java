package co.solinx.forestRaft;

/**
 * Created by linx on 2015/8/28.
 */
public class RaftException extends Exception {

    public RaftException(String message) {
        super(message);
    }

    public RaftException(Throwable cause) {
        super(cause);
    }

    public RaftException() {
        super();
    }

    public RaftException(String message, Throwable cause) {
        super(message, cause);
    }
}
