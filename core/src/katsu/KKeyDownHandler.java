package katsu;

import com.badlogic.gdx.Input;

/**
 * Created by shaun on 07/08/2016.
 */
public class KKeyDownHandler {

    public boolean handle(int keycode) {

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
            K.ui.clearText();
            if (K.text.helpShowing()) {
                K.text.hideHelp();
                K.runner.unpause();
            } else {
                K.text.showHelp();
                K.runner.pauseGame();
            }
        }

        if (keycode == K.settings.getToggleMusicKey()) {
            K.runner.toggleMusic();
        }

        if ((keycode == Input.Keys.F || keycode == Input.Keys.F11)) {
            K.settings.toggleFullScreen();
        }

        K.input.setKeyDown(keycode, true);
        return false;

    }
}
