package pankosample;

import panko.components.PankoGame;
import panko.components.PankoRoom;
import pankosample.rooms.StartRoom;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoSampleGame implements PankoGame {
    @Override
    public ArrayList<PankoRoom> getRooms() {

        return new ArrayList<PankoRoom>(
                Arrays.asList(
                        new StartRoom()
                )
        );
    }
}
