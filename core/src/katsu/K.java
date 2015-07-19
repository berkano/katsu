package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class K {

    private static SpriteBatch activeSpriteBatch;
    private static ShapeRenderer activeShapeRenderer;
    private static SpriteBatch uiSpriteBatch;
    private static ShapeRenderer uiShapeRenderer;
    private static Camera uiCamera;
    private static Camera mainCamera;
    private static KGameRunner runner;
    private static KGame implementation;
    private static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private static KSettings settings;
    private static HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    public static Random random = new Random();
    private static KUI ui;
    private static HashMap<String, Long> explanations = new HashMap<String, Long>();

    public static void exitWithError(String message) {
        // TODO: show alert box in production mode
        KLog.fatal(message);
        exit();
        throw new RuntimeException("Panko game runner closed due to error: "+message);
    }

    public static void exitDueToException(String message, Exception ex) {
        exitWithError(message + "\nCaused by: " + ex.toString());
    }

    public static void exit() {
        KLog.debug("Panko game runner exiting by request.");
        Gdx.app.exit();
    }

    public static SpriteBatch getActiveSpriteBatch() {
        return activeSpriteBatch;
    }

    public static void setActiveSpriteBatch(SpriteBatch activeSpriteBatch) {
        K.activeSpriteBatch = activeSpriteBatch;
    }

    public static void setRunner(KGameRunner runner) {
        K.runner = runner;
    }

    public static KGameRunner getRunner() {
        return runner;
    }

    public static KGame getImplementation() {
        return implementation;
    }

    public static void setImplementation(KGame implementation) {
        K.implementation = implementation;
    }

    public static InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    public static void setInputMultiplexer(InputMultiplexer inputMultiplexer) {
        K.inputMultiplexer = inputMultiplexer;
    }

    public static int getGridSize() {
        return settings.getGridSize();
    }

    public static void setSettings(KSettings settings) {
        K.settings = settings;
    }

    public static KSettings getSettings() {
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


    public static KEntity addEntityToRoom(KRoom room, KEntity entity) {
        room.getEntities().add(entity);
        entity.setRoom(room);
        return entity;
    }

    public static KEntity queueEntityToRoom(KRoom room, KEntity entity) {
        room.getNewEntities().add(entity);
        entity.setRoom(room);
        return entity;
    }


    public static Camera getMainCamera() {
        return mainCamera;
    }

    public static void setMainCamera(Camera mainCamera) {
        K.mainCamera = mainCamera;
    }

    public static void deleteEntitiesOfClassFromRoom(KRoom room, Class clazz) {
        for (KEntity e : room.getEntities()) {
            if (clazz.isInstance(e)) {
                e.destroy();
            }
        }
    }

    public static ShapeRenderer getActiveShapeRenderer() {
        return activeShapeRenderer;
    }

    public static void setActiveShapeRenderer(ShapeRenderer activeShapeRenderer) {
        K.activeShapeRenderer = activeShapeRenderer;
    }

    public static KUI getUI() {
        return ui;
    }

    public static KUI getUi() {
        return ui;
    }

    public static void setUi(KUI ui) {
        K.ui = ui;
    }

    public static void setUI(KUI UI) {
        K.ui = UI;
    }

    public static void pauseGame() {
        runner.pauseGame();
    }

    public static void unPauseGame() {
        K.getUI().setShowingHelp(false);
        runner.unPauseGame();
    }

    public static boolean gamePaused() {
        return runner.gamePaused();
    }

    public static void toggleFullScreenMode() {
        if (Gdx.graphics.isFullscreen()) {
            //Gdx.graphics.setDisplayMode(Game.instance.initialDisplayMode.width, Game.instance.initialDisplayMode.height, false);
            Gdx.graphics.setDisplayMode(getSettings().getHres(), getSettings().getVres(), false);
        } else {
            Gdx.graphics.setDisplayMode(getSettings().getHres(), getSettings().getVres(), true);
        }
    }

    public static SpriteBatch getUiSpriteBatch() {
        return uiSpriteBatch;
    }

    public static void setUiSpriteBatch(SpriteBatch uiSpriteBatch) {
        K.uiSpriteBatch = uiSpriteBatch;
    }

    public static ShapeRenderer getUiShapeRenderer() {
        return uiShapeRenderer;
    }

    public static void setUiShapeRenderer(ShapeRenderer uiShapeRenderer) {
        K.uiShapeRenderer = uiShapeRenderer;
    }

    public static Camera getUiCamera() {
        return uiCamera;
    }

    public static void setUiCamera(Camera uiCamera) {
        K.uiCamera = uiCamera;
    }

    public static void breakpoint() {

    }

    public static void explain(String text) {

        Long lastExplainedThis = explanations.get(text);
        if (lastExplainedThis == null || lastExplainedThis < currentTime() - 2000) {
            getUI().writeText(text);
            explanations.put(text, currentTime());
        }

    }
}
