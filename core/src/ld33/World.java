package ld33;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import katsu.*;
import ld33.entities.MobBase;
import ld33.entities.Monster;

import java.util.ArrayList;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoomBase {

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

        LD33UI ui = (LD33UI)K.getUI();

        ui.getMessageReplacements().put("Monster attacks", "You attack");
        ui.getMessageReplacements().put("attacks Monster", "attacks you");
        ui.getMessageReplacements().put("Monster swings", "You swing");
        ui.getMessageReplacements().put("swings for Monster", "swings for you");
        ui.getMessageReplacements().put("Monster rises ", "You rise ");

        String mapName = "ld33";
        wipeData();
        KTmxHelper.addEntitiesToRoomFromMap(mapName, this);
        player = (Monster) firstInstanceOfClass(Monster.class);

        K.getMainCamera().viewportHeight = K.getWindowHeight() / 4;
        K.getMainCamera().viewportWidth = K.getWindowWidth() / 4;

        K.getUI().setHelpText(KResource.loadText("help.txt"));

        if (LD33Settings.get().startWithPausedHelp) {
            K.getUI().setShowingHelp(true);
        }

        K.getUI().clearText();

        if (LD33Settings.get().startPaused) {
            K.pauseGame();
        }

    }

    @Override
    public void render() {
        super.render();

        if (K.isKeyDown(Input.Keys.R)) {
            if (lastRestart < K.currentTime() - 2000) {
                lastRestart = K.currentTime();
                LD33Sounds.restart.play();
                start();
            }
        }

    }

    @Override
    public void update() {

        super.update();

        if (player.getHealth() <= 0 || player.isDestroyed()) {
            K.getUI().writeText("PERMADEATH paid you a friendly visit. game over. press R to restart");
            K.pauseGame();
        }

        K.getUI().setTopText(player.getStats().toString());
        if (lastMobAttackedByPlayer != null) {
            String enemyName = getLastMobAttackedByPlayer().getClass().getSimpleName();
            K.getUI().setSecondaryText(enemyName + ": " + getLastMobAttackedByPlayer().getStats().toString());
        } else {
            K.getUI().setSecondaryText("");
        }

        if (!K.gamePaused()) {
            if (LD33Settings.get().startWithMusic) {
                if (!hasStartedMusicAtLeastOnce) {
                    LD33Sounds.playMusic();
                    hasStartedMusicAtLeastOnce = true;
                }
            }

        }

        boolean somethingHappened = false;
        KDirection directionToMove = null;

            if (K.isKeyDown(Input.Keys.W)) directionToMove = KDirection.UP;
            if (K.isKeyDown(Input.Keys.A)) directionToMove = KDirection.LEFT;
            if (K.isKeyDown(Input.Keys.S)) directionToMove = KDirection.DOWN;
            if (K.isKeyDown(Input.Keys.D)) directionToMove = KDirection.RIGHT;

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            player.tryFlipMonsterState();
        }

        if (directionToMove != null) {
            somethingHappened = true;
            player.moveRequested(directionToMove);
        }

        if (somethingHappened) {
            K.setLastRogueUpdate(System.currentTimeMillis());
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