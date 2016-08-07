package katsu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class KRunner extends KInputProcessor implements ApplicationListener {

    public abstract ArrayList<KRoom> getRooms();
    public abstract String getResourceRoot();
    public abstract List<Class> getClassLookup();
    public abstract void toggleMusic();

    private ArrayList<KRoom> rooms;
    private Boolean paused = false;

    @Getter @Setter private long lastRogueUpdate = System.currentTimeMillis();

    @Override
    public void create() {

        // Game window
        Gdx.graphics.setTitle(K.settings.getGameName() + " :: " + K.settings.getGameAuthor() + " :: " + K.settings.getGameDescription());

        K.graphics.init();
        K.ui.init();
        K.input.init(this);
        rooms = K.runner.getRooms();
        if (rooms == null || rooms.size() <= 0) {
            exitWithError("No rooms defined!");
        }
        rooms.get(0).start();
    }

    @Override
    public void render() {

        K.graphics.camera.update();
        K.graphics.spriteBatch.setProjectionMatrix(K.graphics.camera.combined);
        K.graphics.shapeRenderer.setProjectionMatrix(K.graphics.camera.combined);
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        K.graphics.spriteBatch.begin();

        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.render();
            }
        }

        K.graphics.spriteBatch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        K.graphics.uiShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        K.ui.renderShadowBoxes();

        K.graphics.uiShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        K.graphics.uiSpriteBatch.begin();
        K.ui.renderText();
        K.graphics.uiSpriteBatch.end();

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
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            K.runner.exit();
        }
        if (keycode == K.settings.getPauseKey()) {
            if (gamePaused()) {
                unpause();
            } else {
                pauseGame();
            }
            return true;
        }
        if (keycode == Input.Keys.H) {
            K.ui.clearText();
            if (K.text.helpShowing()) {
                K.text.hideHelp();
                unpause();
            } else {
                K.text.showHelp();
                pauseGame();
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

    @Override
    public boolean keyUp(int keycode) {
        K.input.setKeyDown(keycode, false);
        return false;
    }

    public void pauseGame() {
        String key = Input.Keys.toString(K.settings.getPauseKey());
        K.ui.writeText("@CYAN Game is paused. Press "+key+" to continue.");
        this.paused = true;
    }

    public void unpause() {
        K.ui.clearText();
        K.text.hideHelp();
        this.paused = false;
    }

    public boolean gamePaused() {
        return this.paused;
    }

    public void exitWithError(String message) {
        K.logger.fatal(message);
        exit();
        throw new RuntimeException("game runner closed due to error: "+message);
    }

    public void exitDueToException(String message, Exception ex) {
        exitWithError(message + "\nCaused by: " + ex.toString());
    }

    public void exit() {
        K.logger.debug("game runner exiting by request.");
        Gdx.app.exit();
    }

}
