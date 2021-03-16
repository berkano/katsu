package katsu.input;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by shaun on 30/07/2016.
 */
public class KInputProcessor implements InputProcessor {

    // Default InputProcessor impl.
    public boolean keyDown(int keycode) {
        return false;
    }
    public boolean keyUp(int keycode) {
        return false;
    }
    public boolean keyTyped(char character) {
        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    public boolean scrolled(int amount) {
        return false;
    }

}
