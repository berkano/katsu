package katsu;

import com.badlogic.gdx.graphics.Color;
import mini73.LevelManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by shaun on 25/03/2017.
 */
public class KObsoleteHandler {
    
    public static KObsoleteHandler ui = KObsoleteHandler.getInstance();
    public static KObsoleteHandler text = KObsoleteHandler.getInstance();

    private static KObsoleteHandler _instance;

    private static KObsoleteHandler getInstance() {
        if (_instance == null) {
            _instance = new KObsoleteHandler();
        }
        return _instance;
    }


    public void writeText(String s) {

    }

    public void drawStringAbsolute(String info, Color black, int i, int i1) {
    }

    public void showHelp() {
    }

    public void clearText() {
    }

    public boolean helpShowing() {
        throw new ObsoleteException();
    }

    public void hideHelp() {

    }

    public void init() {

    }

    public void renderShadowBoxes() {

    }

    public void renderText() {

    }

    public void setTop(String s) {
    }

    public void setSecondary(String s) {
    }
}
