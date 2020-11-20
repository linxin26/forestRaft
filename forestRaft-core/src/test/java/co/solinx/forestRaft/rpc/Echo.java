package co.solinx.forestRaft.rpc;

import java.util.Date;

/**
 * @author linxin
 * @version v1.0
 * @date 2020/11/20.
 */
public class Echo {
    private String message;
    private Date time;

    public Echo(String message, Date time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Echo{" +
                "message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
