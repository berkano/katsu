package panko;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoGameRunner implements ApplicationListener, InputProcessor {

    private PankoGame implementation;

    private ArrayList<PankoRoom> rooms;

    public PankoGameRunner(PankoGame implementation) {
        this.implementation = implementation;
        Panko.setImplementation(implementation);
    }

    @Override
    public void create() {

        rooms = implementation.getRooms();
        if (rooms.size() <= 0) {
            Panko.exitWithError("No rooms defined!");
        }

        startFirstRoom();

    }

    private void startFirstRoom() {
        rooms.get(0).start();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

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
