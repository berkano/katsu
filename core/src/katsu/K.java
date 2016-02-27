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

    // Provided as static for convenience
    public static Random random = new Random();
    public static KLogger logger = new KLogger();
    public static KResource resource = new KResource();
    public static KUtils utils = new KUtils();
    public static KInput input = new KInput();

    // Normally provided by implementation
    public static KGameRunner runner = new KGameRunner();
    public static KSettings settings = new KSettings();
    public static KUI ui = new KUI();

    // Everything else
    private static HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    @Getter @Setter private static int windowWidth;
    @Getter @Setter private static int windowHeight;
    @Getter @Setter private static long lastRogueUpdate = System.currentTimeMillis();

    public static void setKeyDown(int keycode, boolean isDown) {
        keysDown.remove(keycode);
        if (isDown) {
            keysDown.put(keycode, true);
        }
    }

    public static boolean isKeyDown(int keycode) {
        return keysDown.get(keycode) != null;
    }

    public static void toggleFullScreenMode() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), false);
        } else {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), true);
        }
    }

}
