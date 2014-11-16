package panko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by shaun on 16/11/2014.
 */
public class Panko {

    private static PankoGame implementation;
    private static SpriteBatch activeSpriteBatch;

    public static PankoGame getImplementation() {
        return implementation;
    }

    public static void setImplementation(PankoGame implementation) {
        Panko.implementation = implementation;
    }

    public static void exitWithError(String message) {
        // TODO: show alert box in production mode
        PankoLog.fatal(message);
        exit();
        throw new RuntimeException("Panko game runner closed due to error: "+message);
    }

    public static void exitDueToException(String message, Exception ex) {
        exitWithError(message + "\nCaused by: " + ex.toString());
    }

    public static void exit() {
        PankoLog.debug("Panko game runner exiting by request.");
        Gdx.app.exit();
    }

    public static SpriteBatch getActiveSpriteBatch() {
        return activeSpriteBatch;
    }

    public static void setActiveSpriteBatch(SpriteBatch activeSpriteBatch) {
        Panko.activeSpriteBatch = activeSpriteBatch;
    }
}
