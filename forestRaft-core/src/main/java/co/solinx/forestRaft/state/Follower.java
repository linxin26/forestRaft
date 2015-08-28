package co.solinx.forestRaft.state;

import co.solinx.forestRaft.RaftContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linx on 2015/8/28.
 */
public class Follower implements State{

    private Logger logger= LoggerFactory.getLogger(Follower.class);

    public void init(RaftContext context) {
        logger.info("follower");
    }
}
