package mini58;

import com.badlogic.gdx.audio.Sound;
import ld31v2.CampaignMap;
import ld31v2.entities.*;
import panko.PankoGame;
import panko.PankoResource;
import panko.PankoRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini58Game implements PankoGame {

    @Override
    public ArrayList<PankoRoom> getRooms() {
        return null;
    }

    @Override
    public String getResourceRoot() {
        return "mini58";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return null;
    }

    @Override
    public void toggleMusic() {

    }
}
