package katsu;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by shaun on 25/03/2017.
 */
public class KObsolete {
    
    public static KObsolete ui = KObsolete.getInstance();
    public static KObsolete text = KObsolete.getInstance();

    private static KObsolete _instance;
    private boolean strict = false;

    private static KObsolete getInstance() {
        if (_instance == null) {
            _instance = new KObsolete();
        }
        return _instance;
    }


    public void writeText(String s) {
        raiseExceptionIfStrict();
    }

    public void drawStringAbsolute(String info, Color black, int i, int i1) {
        raiseExceptionIfStrict();
    }

    public void showHelp() {
        raiseExceptionIfStrict();
    }

    public void clearText() {
        raiseExceptionIfStrict();
    }

    public boolean helpShowing() {
        raiseExceptionIfStrict();
        return false;
    }

    public void hideHelp() {
        raiseExceptionIfStrict();
    }

    public void init() {
        raiseExceptionIfStrict();
    }

    public void renderShadowBoxes() {
        raiseExceptionIfStrict();
    }

    public void renderText() {
        raiseExceptionIfStrict();
    }

    public void setTop(String s) {
        raiseExceptionIfStrict();
    }

    public void setSecondary(String s) {
        raiseExceptionIfStrict();
    }

    private void raiseExceptionIfStrict() {
        if (strict) {
            throw new KNotImplementedException();
        }
    }
}
