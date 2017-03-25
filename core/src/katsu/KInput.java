package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import lombok.Getter;
import lombok.Setter;
import mini73.UnfinishedBusinessException;

import java.util.HashMap;

/**
 * Created by shaun on 27/02/2016.
 */
public class KInput {

    @Getter @Setter private InputMultiplexer multiplexer = new InputMultiplexer();

    private HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Boolean> keysTyped = new HashMap<Integer, Boolean>();

    public boolean keyDown(int keycode) {
        keysDown.put(keycode, true);
        keysTyped.put(keycode, true);
        return handleKeyDown(keycode);
    }

    private boolean handleKeyDown(int keycode) {

        if (keycode == Input.Keys.ESCAPE) {
            K.runner.exit();
        }

        if (keycode == K.settings.getPauseKey()) {
            if (K.runner.gamePaused()) {
                K.runner.unpause();
            } else {
                K.runner.pauseGame();
            }
            return true;
        }

        if (keycode == Input.Keys.H) {
            K.obsolete.ui.clearText();
            if (K.obsolete.text.helpShowing()) {
                K.obsolete.text.hideHelp();
                K.runner.unpause();
            } else {
                K.obsolete.text.showHelp();
                K.runner.pauseGame();
            }
        }

        if (keycode == K.settings.getToggleMusicKey()) {
            K.runner.toggleMusic();
        }

        if ((keycode == Input.Keys.F || keycode == Input.Keys.F11)) {
            K.settings.toggleFullScreen();
        }

        return false;

    }

    public boolean keyUp(int keycode) {
        keysDown.put(keycode, false);
        return false;
    }

    public boolean isKeyDown(int keycode) {
        if (keysDown.get(keycode) == null) return false;
        return keysDown.get(keycode);
    }

    public void init(KRunner runner) {

        Gdx.input.setInputProcessor(K.input.getMultiplexer());
        getMultiplexer().addProcessor(runner);

    }

    public boolean wasKeyTyped(int keycode) {
        if (keysTyped.get(keycode) == null) return false;
        keysTyped.remove(keycode);
        return true;
    }

}
