package panko.components;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoGameRunner implements ApplicationListener, InputProcessor {

    private PankoGame implementation;

    private ArrayList<PankoRoom> rooms;
    private ArrayList<PankoObjekt> entities;

    public PankoGameRunner(PankoGame implementation) {
        this.implementation = implementation;
    }

    @Override
    public void create() {

        rooms = implementation.getRooms();
        if (rooms.size() <= 0) {
            exitWithError("No rooms defined!");
        }
        entities = new ArrayList<PankoObjekt>();

        startFirstRoom();

    }

    private void exitWithError(String message) {
        Panko.showAlert("Sorry, an error occurred: \n\n"+message);
        Gdx.app.exit();
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
