package katsu;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaun on 15/11/2014.
 */
public interface KGame {
    ArrayList<KRoom> getRooms();

    String getResourceRoot();

    HashMap<String,Class> getClassLookup();

    void toggleMusic();
}
