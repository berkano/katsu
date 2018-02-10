package katsu.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import katsu.K;
import katsu.game.KGame;
import lombok.Getter;
import lombok.Setter;

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
            K.game.exit();
        }

        if (keycode == K.settings.getPauseKey()) {
            if (K.game.gamePaused()) {
                K.game.unpause();
            } else {
                K.game.pauseGame();
            }
            return true;
        }

        if (keycode == K.settings.getToggleMusicKey()) {
            K.game.toggleMusic();
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

    public void init(KGame runner) {

        Gdx.input.setInputProcessor(K.input.getMultiplexer());
        getMultiplexer().addProcessor(runner);

    }

    public boolean wasKeyTyped(int keycode) {
        if (keysTyped.get(keycode) == null) return false;
        keysTyped.remove(keycode);
        return true;
    }

}
