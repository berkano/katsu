package pankosample.entities;

import panko.PankoEntityBase;
import panko.PankoLog;

/**
 * Created by shaun on 16/11/2014.
 */
public class Robot extends PankoEntityBase {
    @Override
    public boolean keyTyped(char character) {
        PankoLog.debug("Robot detected key " + character);
        return false;
    }
}
