package katsu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class KGameRunner implements ApplicationListener, InputProcessor {

    private ArrayList<KRoom> rooms;

    private Boolean paused = false;

    public KGameRunner() {
        K.setRunner(this);
    }

    @Override
    public void create() {

        Gdx.graphics.setTitle(K.getSettings().getGameName() + " :: " + K.getSettings().getGameAuthor() + " :: " + K.getSettings().getGameDescription());
        K.setActiveSpriteBatch(new SpriteBatch());
        K.setActiveShapeRenderer(new ShapeRenderer());

        K.setUiSpriteBatch(new SpriteBatch());
        K.setUiShapeRenderer(new ShapeRenderer());

        Gdx.input.setInputProcessor(K.getInputMultiplexer());
        K.getInputMultiplexer().addProcessor(this);

        rooms = K.getImplementation().getRooms();
        if (rooms == null || rooms.size() <= 0) {
            K.exitWithError("No rooms defined!");
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 1024;

        K.setMainCamera(new OrthographicCamera(viewportSize, viewportSize * (h / w)));
        K.getMainCamera().position.set(512, 768/2, 0);
        K.getMainCamera().update();

        K.setUiCamera(new OrthographicCamera(viewportSize, viewportSize * (h / w)));
        K.getUiCamera().position.set(512, 768/2, 0);
        K.getUiCamera().update();

        K.setUI(new KUI());

        rooms.get(0).start();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        K.getMainCamera().update();
        K.getActiveSpriteBatch().setProjectionMatrix(K.getMainCamera().combined);
        K.getActiveShapeRenderer().setProjectionMatrix(K.getMainCamera().combined);

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        K.getActiveSpriteBatch().begin();
        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.render();
            }
        }
        K.getActiveSpriteBatch().end();

        K.getUI().render();

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
            K.exit();
        }

        if (keycode == K.getSettings().getPauseKey()) {
            if (gamePaused()) {
                unPauseGame();
            } else {
                pauseGame();
            }
            return true;
        }

        if (keycode == Input.Keys.H) {
            if (K.getUI().isShowingHelp()) {
                K.getUI().setShowingHelp(false);
            } else {
                K.getUI().setShowingHelp(true);
                pauseGame();
            }
        }

        if (keycode == K.getSettings().getToggleMusicKey()) {
            K.getImplementation().toggleMusic();
        }

        if ((keycode == Input.Keys.F || keycode == Input.Keys.F11)) {
            K.toggleFullScreenMode();
        }



        K.setKeyDown(keycode, true);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        K.setKeyDown(keycode, false);

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
        String key = Input.Keys.toString(K.getSettings().getPauseKey());
        K.getUI().writeText("@CYAN Game is paused. Press "+key+" to continue.");
        this.paused = true;
    }

    public void unPauseGame() {
        K.getUI().clearText();
        K.getUI().setShowingHelp(false);
        this.paused = false;
    }

    public boolean gamePaused() {
        return this.paused;
    }
}
