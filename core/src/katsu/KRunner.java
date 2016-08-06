package katsu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        K.ui.init();
        K.input.initalise(this);
        rooms = K.runner.getRooms();
        if (rooms == null || rooms.size() <= 0) {
            exitWithError("No rooms defined!");
        }
        rooms.get(0).start();
    }

    @Override
    public void render() {
        K.ui.preGlobalRender();
        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.render();
            }
        }
        K.ui.postGlobalRender();
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
