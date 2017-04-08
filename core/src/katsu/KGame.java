package katsu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;
import mini73.UnfinishedBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class KGame extends KInputProcessor implements ApplicationListener {

    public abstract ArrayList<KRoom> getRooms();
    public abstract String getResourceRoot();
    public abstract List<Class> getClassLookup();
    public abstract void toggleMusic();

    private ArrayList<KRoom> rooms;
    private Boolean paused = false;
    boolean doneFirstUpdate = false;

    Logger logger = LoggerFactory.getLogger(KGame.class);

    @Getter @Setter private long lastRogueUpdate = System.currentTimeMillis();

    @Override
    public void create() {

        // Game window
        Gdx.graphics.setTitle(K.settings.getGameName() + " :: " + K.settings.getGameAuthor() + " :: " + K.settings.getGameDescription());

        K.graphics.init();
//        K.obsolete.ui.init();
        K.input.init(this);
        rooms = K.game.getRooms();
        if (rooms == null || rooms.size() <= 0) {
            exitWithError("No rooms defined!");
        }
    }

    public void start() {
        rooms.get(0).start();
    }

    @Override
    public void render() {

        update();

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

//        K.obsolete.ui.renderShadowBoxes();

        K.graphics.uiShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        K.graphics.uiSpriteBatch.begin();
//        K.obsolete.ui.renderText();
        K.graphics.uiSpriteBatch.end();

    }

    public void update() {

        if (!doneFirstUpdate) {
            beforeFirstUpdate();
            doneFirstUpdate = true;
        }

        if (paused) return;

        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.update();
            }
        }

    }

    public void beforeFirstUpdate() {
        start();
    }

    @Override
    public boolean keyDown(int keycode) {
        return K.input.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return K.input.keyUp(keycode);
    }

    public void pauseGame() {
        this.paused = true;
    }

    public void unpause() {
        this.paused = false;
    }

    public boolean gamePaused() {
        return this.paused;
    }

    public void exitWithError(String message) {
        logger.error(message);
        exit();
        throw new RuntimeException("game game closed due to error: "+message);
    }

    public void exitDueToException(String message, Exception ex) {
        exitWithError(message + "\nCaused by: " + ex.toString());
    }

    public void exit() {
        logger.debug("game game exiting by request.");
        Gdx.app.exit();
    }

    public KRoom roomForClass(Class clazz) {
        UnfinishedBusinessException.raise();
        return null;
    }

    public abstract KSettings buildSettings();
}
