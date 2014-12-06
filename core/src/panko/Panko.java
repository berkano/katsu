package panko;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld31.Universe;
import ld31.entities.Ship;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class Panko {

    private static SpriteBatch activeSpriteBatch;
    private static Camera mainCamera;
    private static PankoGameRunner runner;
    private static PankoGame implementation;
    private static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private static PankoSettings settings;
    private static HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    public static Random random = new Random();

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

    public static long currentTime() {
        return System.currentTimeMillis();
    }

    public static boolean keyPressed(int keycode) {
        return false;
    }

    public static void setKeyDown(int keycode, boolean isDown) {
        keysDown.remove(keycode);
        if (isDown) {
            keysDown.put(keycode, true);
        }
    }

    public static boolean isKeyDown(int keycode) {
        return keysDown.get(keycode) != null;
    }


    public static PankoEntity addEntityToRoom(PankoRoom room, PankoEntity entity) {
        room.getEntities().add(entity);
        entity.setRoom(room);
        return entity;
    }

    public static PankoEntity queueEntityToRoom(PankoRoom room, PankoEntity entity) {
        room.getNewEntities().add(entity);
        entity.setRoom(room);
        return entity;
    }


    public static Camera getMainCamera() {
        return mainCamera;
    }

    public static void setMainCamera(Camera mainCamera) {
        Panko.mainCamera = mainCamera;
    }

    public static void queueEntityToTop(PankoRoom room, PankoEntity entity) {
        room.getOnTopQueue().add(entity);
    }
}
