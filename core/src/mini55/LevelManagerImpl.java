package mini55;

import katsu.Documentation;
import katsu.Game;
import katsu.LevelManager;
import katsu.UI;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 12/05/13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class LevelManagerImpl implements LevelManager {

    @Override
    public String nextLevel(String fromLevel) {
        return "0000";
    }

    @Override
    public String tmxForLevelCode(String code) {

        if (code.equals("0000")) return "0000";
        return "";
    }

    @Override
    public void showLevelInstructions(UI ui, Game game) {
    }

    @Override
    public void showHelp(int helpPage, UI ui) {

        String[] lines = Documentation.getHelpPage(helpPage).split("\n");
        ui.writeText(String.format("*** HELP: Page (%s of %s). Press H for next page. ***", helpPage, Documentation.getNumHelpPages()));
        for (String line : lines) {
            ui.writeText(line);
        }

    }

    @Override
    public HashMap<String, Class> getTmxClassMapping() {

        HashMap<String, Class> classLookup = new HashMap<String, Class>();

        // Example
//        classLookup.put("Diamond", Diamond.class);

        return classLookup;

    }
}
