package katsu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class KRunner implements ApplicationListener, InputProcessor {

    private ArrayList<KRoom> rooms;

    private Boolean paused = false;

    public ArrayList<KRoom> getRooms() {
        throw new NotImplementedException();
    }

    public String getResourceRoot() {
        throw new NotImplementedException();
    }

    public List<Class> getClassLookup() {
        throw new NotImplementedException();
    }

    public void toggleMusic() {
        throw new NotImplementedException();
    }

    public KUI createUI() {
        throw new NotImplementedException();
    }

    @Override
    public void create() {

        Gdx.graphics.setTitle(K.settings.getGameName() + " :: " + K.settings.getGameAuthor() + " :: " + K.settings.getGameDescription());

        K.ui = K.runner.createUI();

        K.ui.setActiveSpriteBatch(new SpriteBatch());
        K.ui.setActiveShapeRenderer(new ShapeRenderer());

        K.ui.setUiSpriteBatch(new SpriteBatch());
        K.ui.setUiShapeRenderer(new ShapeRenderer());

        Gdx.input.setInputProcessor(K.input.getMultiplexer());
        K.input.getMultiplexer().addProcessor(this);

        rooms = K.runner.getRooms();
        if (rooms == null || rooms.size() <= 0) {
            K.runner.exitWithError("No rooms defined!");
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 1024;

        K.ui.setMainCamera(new OrthographicCamera(viewportSize, viewportSize * (h / w)));
        K.ui.getMainCamera().position.set(512, 768 / 2, 0);
        K.ui.getMainCamera().update();

        K.ui.setUiCamera(new OrthographicCamera(viewportSize, viewportSize * (h / w)));
        K.ui.getUiCamera().position.set(512, 768 / 2, 0);
        K.ui.getUiCamera().update();

        rooms.get(0).start();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        K.ui.getMainCamera().update();
        K.ui.getActiveSpriteBatch().setProjectionMatrix(K.ui.getMainCamera().combined);
        K.ui.getActiveShapeRenderer().setProjectionMatrix(K.ui.getMainCamera().combined);

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        K.ui.getActiveSpriteBatch().begin();
        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.render();
            }
        }
        K.ui.getActiveSpriteBatch().end();

        K.ui.render();

        update();

    }

    private void update() {

        if (paused) return;

        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.update();
            }
        }

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

        if (keycode == Input.Keys.ESCAPE) {
            K.runner.exit();
        }

        if (keycode == K.settings.getPauseKey()) {
            if (gamePaused()) {
                unPauseGame();
            } else {
                pauseGame();
            }
            return true;
        }

        if (keycode == Input.Keys.H) {
            K.ui.clearText();
            if (K.ui.isShowingHelp()) {
                K.ui.setShowingHelp(false);
                K.runner.unPauseGame();
            } else {
                K.ui.setShowingHelp(true);
                pauseGame();
            }
        }

        if (keycode == K.settings.getToggleMusicKey()) {
            K.runner.toggleMusic();
        }

        if ((keycode == Input.Keys.F || keycode == Input.Keys.F11)) {
            K.ui.toggleFullScreenMode();
        }



        K.input.setKeyDown(keycode, true);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        K.input.setKeyDown(keycode, false);

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

    public void pauseGame() {
        String key = Input.Keys.toString(K.settings.getPauseKey());
        K.ui.writeText("@CYAN Game is paused. Press "+key+" to continue.");
        this.paused = true;
    }

    public void unPauseGame() {
        K.ui.clearText();
        K.ui.setShowingHelp(false);
        this.paused = false;
    }

    public boolean gamePaused() {
        return this.paused;
    }

    public static void exitWithError(String message) {
        // TODO: show alert box in production mode
        K.logger.fatal(message);
        exit();
        throw new RuntimeException("game runner closed due to error: "+message);
    }

    public static void exitDueToException(String message, Exception ex) {
        exitWithError(message + "\nCaused by: " + ex.toString());
    }

    public static void exit() {
        K.logger.debug("game runner exiting by request.");
        Gdx.app.exit();
    }

}
