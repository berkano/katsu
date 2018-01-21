package ld38;

import katsu.KEntity;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shaun on 26/04/2017.
 */
public class TrollBuilder {

    Logger logger = LoggerFactory.getLogger(TrollBuilder.class);
    TrollCastleGame game;
    TrollCastleSounds sounds;
    TrollManager manager;
    TrollMap room;

    public TrollBuilder(TrollCastleGame game, TrollManager manager, TrollMap room) {
        this.game = game;
        this.manager = manager;
        this.room = room;
        this.sounds = game.sounds;
    }

    public void build() {

        Troll selectedTroll = manager.getSelectedTroll();

        // do we have a selected troll?
        if (selectedTroll == null) {
            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
            return;
        }

        // work out what we're on top of, first.

        Class allowedBuild = determineAllowedBuild(selectedTroll.getX() + 2, selectedTroll.getY() + 2);

        if (allowedBuild == null) {
            // we are not in a suitable place for building
            selectedTroll.say("we got to build on sand!");
            return;
        }

        if (allowedBuild == Wall.class) {
            buildWall();
            return;
        }

        if (allowedBuild == Tower.class) {
            buildTower();
            return;
        }

        if (allowedBuild == GoldTower.class) {
            buildGoldTower();
        }

    }

    private void buildGoldTower() {

        Troll selectedTroll = manager.getSelectedTroll();

        if (game.towersBuilt < 4) {
            selectedTroll.say("[RED]build more tower!");
            return;
        }

        if (game.gold < 5) {
            selectedTroll.say("[RED]need 5 gold");
            return;
        }
        game.gold -= 5;

        GoldTower tower = new GoldTower();
        tower.setX(selectedTroll.getX());
        tower.setY(selectedTroll.getY());
        room.addNewEntity(tower);
        sounds.build.play();
        selectedTroll.say("built gold tower!");
    }

    private Class determineAllowedBuild(int x, int y) {

        Class result = null;

        List<KEntity> under = room.findEntitiesAtPoint(x, y);

        // we assume entities are in correct z order
        for (KEntity e : under) {
            if (e instanceof Sand) result = Wall.class;
            if (e instanceof Wall) result = Tower.class;
            if (e instanceof Tower) result = GoldTower.class;
        }

        return result;

    }

    private void buildTower() {

        Troll troll = manager.getSelectedTroll();

        if (!sufficientWallsCheck()) return;
        if (!correctTowerLocationCheck()) return;
        if (!sufficientStoneCheck()) return;

        game.stone -= 10;
        Tower tower = new Tower();
        tower.setX(troll.getX());
        tower.setY(troll.getY());
        room.addNewEntity(tower);
        sounds.build.play();
        troll.say("built tower!");

    }

    private boolean sufficientStoneCheck() {

        Troll troll = manager.getSelectedTroll();

        if (game.stone < 10) {
            troll.say("[RED]need 10 stone!");
            return false;
        }
        return true;

    }

    private boolean correctTowerLocationCheck() {

        Troll troll = manager.getSelectedTroll();

        int x = troll.getX();
        int y = troll.getY();

        logger.info("Attempting to build tower at precise position " + x + "," + y);

        boolean allowedTowerLocation = false;
        if (x == 144 && y == 112) allowedTowerLocation = true;
        if (x == 208 && y == 112) allowedTowerLocation = true;
        if (x == 208 && y == 48) allowedTowerLocation = true;
        if (x == 144 && y == 48) allowedTowerLocation = true;

        if (!allowedTowerLocation) {
            troll.say("[RED]no build tower here!");
            return false;
        }

        return true;

    }

    private boolean sufficientWallsCheck() {

        Troll troll = manager.getSelectedTroll();

        if (game.wallsBuilt < 16) {
            if (!TrollDevFlags.skipWallRule) {
                troll.say("[RED]build more walls!");
                return false;
            }
        }
        return true;
    }

    private void buildWall() {

        Troll troll = manager.getSelectedTroll();
        if (game.stone < 3) {
            troll.say("need 3 stone.");
            return;
        }
        game.stone -= 3;
        Wall wall = new Wall();
        wall.setX(troll.getX());
        wall.setY(troll.getY());
        room.addNewEntity(wall);
        troll.say("built wall.");
        sounds.build.play();

    }
}
