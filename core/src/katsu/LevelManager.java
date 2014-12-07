package katsu;

import java.util.HashMap;

/**
 * Created by shaun on 02/11/2014.
 */
public interface LevelManager {

    String nextLevel(String fromLevel);

    String tmxForLevelCode(String code);

    void showLevelInstructions(UI ui, Game game);

    void showHelp(int helpPage, UI ui);

    HashMap<String, Class> getTmxClassMapping();
}
