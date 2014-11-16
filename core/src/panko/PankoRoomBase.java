package panko;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class PankoRoomBase implements PankoRoom, InputProcessor {

    protected ArrayList<PankoEntity> entities;
    private boolean active;

    @Override
    public void start() {
        setActive(true);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void render() {
        for (PankoEntity e : entities) {
            e.render();
        }
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
