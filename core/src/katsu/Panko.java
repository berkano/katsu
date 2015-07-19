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
public class Panko {

    private static SpriteBatch activeSpriteBatch;
    private static ShapeRenderer activeShapeRenderer;
    private static SpriteBatch uiSpriteBatch;
    private static ShapeRenderer uiShapeRenderer;
    private static Camera uiCamera;
    private static Camera mainCamera;
    private static PankoGameRunner runner;
    private static PankoGame implementation;
    private static InputMultiplexer inputMultiplexer = new InputMultiplexer();
    private static PankoSettings settings;
    private static HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    public static Random random = new Random();
    private static PankoUI ui;
    private static HashMap<String, Long> explanations = new HashMap<String, Long>();

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

    public static void deleteEntitiesOfClassFromRoom(PankoRoom room, Class clazz) {
        for (PankoEntity e : room.getEntities()) {
            if (clazz.isInstance(e)) {
                queueEntityToRemove(room, e);
            }
        }
    }

    private static void queueEntityToRemove(PankoRoom room, PankoEntity e) {
        room.getDeadEntities().add(e);
    }

    public static ShapeRenderer getActiveShapeRenderer() {
        return activeShapeRenderer;
    }

    public static void setActiveShapeRenderer(ShapeRenderer activeShapeRenderer) {
        Panko.activeShapeRenderer = activeShapeRenderer;
    }

    public static PankoUI getUI() {
        return ui;
    }

    public static PankoUI getUi() {
        return ui;
    }

    public static void setUi(PankoUI ui) {
        Panko.ui = ui;
    }

    public static void setUI(PankoUI UI) {
        Panko.ui = UI;
    }

    public static void queueEntityToTop(PankoEntity entity) {
        queueEntityToTop(entity.getRoom(), entity);
    }

    public static void queueRemoveEntity(PankoEntity entity) {
        entity.getRoom().getDeadEntities().add(entity);
        entity.setBeingRemoved(true);
    }

    public static void pauseGame() {
        runner.pauseGame();
    }

    public static void unPauseGame() {
        Panko.getUI().setShowingHelp(false);
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
        Panko.uiSpriteBatch = uiSpriteBatch;
    }

    public static ShapeRenderer getUiShapeRenderer() {
        return uiShapeRenderer;
    }

    public static void setUiShapeRenderer(ShapeRenderer uiShapeRenderer) {
        Panko.uiShapeRenderer = uiShapeRenderer;
    }

    public static Camera getUiCamera() {
        return uiCamera;
    }

    public static void setUiCamera(Camera uiCamera) {
        Panko.uiCamera = uiCamera;
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
