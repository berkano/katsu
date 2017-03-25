package ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import katsu.*;
import ld33.entities.MobBase;
import ld33.entities.Monster;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoom {

    private MobBase lastMobAttackedByPlayer;
    private boolean hasStartedMusicAtLeastOnce = false;

    public World() {
        super();
    }

    private Monster player;

    public Monster getPlayer() {
        return player;
    }

    public void setPlayer(Monster player) {
        this.player = player;
    }

    private long lastRestart = System.currentTimeMillis();

    @Override
    public void start() {
        super.start();

//        LD33UI ui = (LD33UI)(Object)K.obsolete.ui;
//
//        ui.getMessageReplacements().put("Monster attacks", "You attack");
//        ui.getMessageReplacements().put("attacks Monster", "attacks you");
//        ui.getMessageReplacements().put("Monster swings", "You swing");
//        ui.getMessageReplacements().put("swings for Monster", "swings for you");
//        ui.getMessageReplacements().put("Monster rises ", "You rise ");

        String mapName = "ld33";
        wipeData();
        loadFromTiledMap(mapName);
        player = (Monster) firstInstanceOfClass(Monster.class);

        K.graphics.camera.viewportHeight = K.settings.getWindowHeight() / 4;
        K.graphics.camera.viewportWidth = K.settings.getWindowWidth() / 4;

        if (LD33Settings.get().startWithPausedHelp) {
            K.obsolete.text.showHelp();
        }

        K.obsolete.ui.clearText();

        if (LD33Settings.get().startPaused) {
            K.runner.pauseGame();
        }

    }

    @Override
    public void render() {
        super.render();

        if (K.input.isKeyDown(Input.Keys.R)) {
            if (lastRestart < K.utils.currentTime() - 2000) {
                lastRestart = K.utils.currentTime();
                LD33Sounds.restart.play();
                start();
            }
        }

    }

    @Override
    public void update() {

        super.update();

        if (player.getHealth() <= 0 || player.isDestroyed()) {
            K.obsolete.ui.writeText("PERMADEATH paid you a friendly visit. game over. press R to restart");
            K.runner.pauseGame();
        }

        K.obsolete.text.setTop(player.getStats().toString());
        if (lastMobAttackedByPlayer != null) {
            String enemyName = getLastMobAttackedByPlayer().getClass().getSimpleName();
            K.obsolete.text.setSecondary(enemyName + ": " + getLastMobAttackedByPlayer().getStats().toString());
        } else {
            K.obsolete.text.setSecondary("");
        }

        if (!K.runner.gamePaused()) {
            if (LD33Settings.get().startWithMusic) {
                if (!hasStartedMusicAtLeastOnce) {
                    LD33Sounds.playMusic();
                    hasStartedMusicAtLeastOnce = true;
                }
            }

        }

        boolean somethingHappened = false;
        KDirection directionToMove = null;

            if (K.input.isKeyDown(Input.Keys.W)) directionToMove = KDirection.UP;
            if (K.input.isKeyDown(Input.Keys.A)) directionToMove = KDirection.LEFT;
            if (K.input.isKeyDown(Input.Keys.S)) directionToMove = KDirection.DOWN;
            if (K.input.isKeyDown(Input.Keys.D)) directionToMove = KDirection.RIGHT;

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            player.tryFlipMonsterState();
        }

        if (directionToMove != null) {
            somethingHappened = true;
            player.tryMove(directionToMove);
        }

        if (somethingHappened) {
            K.runner.setLastRogueUpdate(System.currentTimeMillis());
            player.doEnemyPathFinding();
        }

    }

    public void setLastMobAttackedByPlayer(MobBase lastMobAttackedByPlayer) {
        this.lastMobAttackedByPlayer = lastMobAttackedByPlayer;
    }

    public MobBase getLastMobAttackedByPlayer() {
        return lastMobAttackedByPlayer;
    }
}