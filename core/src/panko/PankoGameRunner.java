package panko;

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
public class PankoGameRunner implements ApplicationListener, InputProcessor {

    private SpriteBatch mainSpriteBatch;
    private ShapeRenderer mainShapeRenderer;
    private OrthographicCamera camera;
    private PankoUI ui;
    private boolean paused = false;

    private ArrayList<PankoRoom> rooms;

    public PankoGameRunner() {
        Panko.setRunner(this);
    }

    @Override
    public void create() {

        Gdx.graphics.setTitle(Panko.getSettings().getGameName() + " :: " + Panko.getSettings().getGameAuthor() + " :: " + Panko.getSettings().getGameDescription());

        mainSpriteBatch = new SpriteBatch();
        mainShapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(Panko.getInputMultiplexer());
        Panko.getInputMultiplexer().addProcessor(this);

        rooms = Panko.getImplementation().getRooms();
        if (rooms == null || rooms.size() <= 0) {
            Panko.exitWithError("No rooms defined!");
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 1024;
        camera = new OrthographicCamera(viewportSize, viewportSize * (h / w));
        Panko.setMainCamera(camera);
        camera.position.set(512, 768/2, 0);
        //camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        ui = new PankoUI();
        Panko.setUI(ui);

        rooms.get(0).start();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        camera.update();
        mainSpriteBatch.setProjectionMatrix(camera.combined);
        mainShapeRenderer.setProjectionMatrix(camera.combined);

        Panko.setActiveSpriteBatch(mainSpriteBatch);
        Panko.setActiveShapeRenderer(mainShapeRenderer);

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Panko.getActiveSpriteBatch().begin();
        for (PankoRoom room : rooms) {
            if (room.isActive()) {
                room.render();
            }
        }
        Panko.getActiveSpriteBatch().end();

        ui.render();

        update();

    }

    private void update() {

        if (paused) return;

        for (PankoRoom room : rooms) {
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
            Panko.exit();
        }

        if (keycode == Input.Keys.P) {
            if (gamePaused()) {
                unPauseGame();
            } else {
                pauseGame();
            }
            return true;
        }

        if (keycode == Input.Keys.H) {
            if (Panko.getUI().isShowingHelp()) {
                Panko.getUI().setShowingHelp(false);
            } else {
                Panko.getUI().setShowingHelp(true);
                pauseGame();
            }
        }

        if (keycode == Input.Keys.M) {
            Panko.getImplementation().toggleMusic();
        }

        if ((keycode == Input.Keys.F || keycode == Input.Keys.F11)) {
            Panko.toggleFullScreenMode();
        }



        Panko.setKeyDown(keycode, true);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        Panko.setKeyDown(keycode, false);

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
        Panko.getUI().writeText("@CYAN Game is paused. Press P to continue.");
        this.paused = true;
    }

    public void unPauseGame() {
        Panko.getUI().clearText();
        Panko.getUI().setShowingHelp(false);
        this.paused = false;
    }

    public boolean gamePaused() {
        return this.paused;
    }
}
