package katsu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 15/11/2014.
 */
public interface KGame {

    ArrayList<KRoom> getRooms();

    String getResourceRoot();

    List<Class> getClassLookup();

    void toggleMusic();

    KUI createUI();

}
