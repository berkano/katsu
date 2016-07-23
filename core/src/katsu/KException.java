package katsu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shaun on 21/07/2016.
 */
public class KException extends RuntimeException {

    Logger logger = LoggerFactory.getLogger(KException.class);

    public KException(Exception causer) {
        logger.error(causer.toString());
    }

}
