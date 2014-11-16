package pankosample.rooms;

import panko.components.Panko;
import panko.components.PankoRoom;

/**
 * Created by shaun on 16/11/2014.
 */
public class StartRoom implements PankoRoom {
    @Override
    public void start() {
        Panko.showAlert("Start room started!!");
    }
}
