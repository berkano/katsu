package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class K {

    // Instances deliberately exposed as static for convenience
    public static Random random = new Random();
    public static KLogger logger = new KLogger();
    public static KResource resources = new KResource();
    public static InputMultiplexer inputs = new InputMultiplexer();

    // Usually provided by implementation
    @Getter @Setter public static KGameRunner runner = new KGameRunner();
    @Getter @Setter public static KSettings settings = new KSettings();

    // Everything else
    private static HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    private static KUI ui;
    private static HashMap<String, Long> explanations = new HashMap<String, Long>();
    private static int windowWidth;
    private static int windowHeight;
    private static long lastRogueUpdate = System.currentTimeMillis();

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

    public static KUI getUI() {
        return ui;
    }

    public static void setUI(KUI UI) {
        K.ui = UI;
    }

    public void pauseGame() {
        runner.pauseGame();
    }

    public void unPauseGame() {
        K.getUI().setShowingHelp(false);
        runner.unPauseGame();
    }

    public static boolean gamePaused() {
        return runner.gamePaused();
    }

    public static void toggleFullScreenMode() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), false);
        } else {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), true);
        }
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

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static void setWindowWidth(int windowWidth) {
        K.windowWidth = windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static void setWindowHeight(int windowHeight) {
        K.windowHeight = windowHeight;
    }

    public static long getLastRogueUpdate() {
        return lastRogueUpdate;
    }

    public static void setLastRogueUpdate(long lastRogueUpdate) {
        K.lastRogueUpdate = lastRogueUpdate;
    }

    public static List<Class> buildClassList(Class... classes ) {
        ArrayList<Class> classList = new ArrayList<Class>();
        for (Class clazz : classes) {
            classList.add(clazz);
        }
        return classList;

    }
}
