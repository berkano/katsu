package panko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by shaun on 16/11/2014.
 */
public class Panko {

    private static SpriteBatch activeSpriteBatch;
    private static PankoGameRunner runner;
    private static PankoGame implementation;
    private static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private static PankoSettings settings;

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

    public static void setRunner(PankoGameRunner runner) {
        Panko.runner = runner;
    }

    public static PankoGameRunner getRunner() {
        return runner;
    }

    public static PankoGame getImplementation() {
        return implementation;
    }

    public static void setImplementation(PankoGame implementation) {
        Panko.implementation = implementation;
    }

    public static InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public static void setInputMultiplexer(InputMultiplexer inputMultiplexer) {
        Panko.inputMultiplexer = inputMultiplexer;
    }

    public static int getGridSize() {
        return settings.getGridSize();
    }

    public static void setSettings(PankoSettings settings) {
        Panko.settings = settings;
    }

    public static PankoSettings getSettings() {
        return settings;
    }
}
