package panko;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoGameRunner implements ApplicationListener, InputProcessor {

    private SpriteBatch mainSpriteBatch;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private ArrayList<PankoRoom> rooms;

    public PankoGameRunner() {
        Panko.setRunner(this);
    }

    @Override
    public void create() {

        debugRenderer = new Box2DDebugRenderer();

        Gdx.graphics.setTitle(Panko.getSettings().getGameName() + " :: " + Panko.getSettings().getGameAuthor() + " :: " + Panko.getSettings().getGameDescription());

        mainSpriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(Panko.getInputMultiplexer());
        Panko.getInputMultiplexer().addProcessor(this);

        rooms = Panko.getImplementation().getRooms();
        if (rooms.size() <= 0) {
            Panko.exitWithError("No rooms defined!");
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 3000;
        camera = new OrthographicCamera(viewportSize, viewportSize * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        rooms.get(0).start();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        camera.update();
        mainSpriteBatch.setProjectionMatrix(camera.combined);

        Panko.setActiveSpriteBatch(mainSpriteBatch);

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Panko.getActiveSpriteBatch().begin();
        for (PankoRoom room : rooms) {
            if (room.isActive()) {
                room.render();
                debugRenderer.render(room.getWorld(), camera.combined);
            }
        }
        Panko.getActiveSpriteBatch().end();

        update();

    }

    private void update() {

        for (PankoRoom room : rooms) {
            if (room.isActive()) {
                room.update();
                room.getWorld().step(1/60f, 6, 2);
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

}
