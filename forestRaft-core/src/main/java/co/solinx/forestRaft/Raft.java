package co.solinx.forestRaft;

/**
 * Created by linx on 2015/8/28.
 */
public interface Raft {

    enum StateType{
        START,FOLLOWER,LEADER,CANDIDATE
    }

     void init();

}
