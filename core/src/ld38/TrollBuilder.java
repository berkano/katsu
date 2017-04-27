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
    TrollManager manager;
    Map room;

    public TrollBuilder(TrollCastleGame game, TrollManager manager, Map room) {
        this.game = game;
        this.manager = manager;
        this.room = room;
    }

    public void build() {

        Troll selectedTroll = manager.getSelectedTroll();

        // do we have a selected troll?
        if (selectedTroll == null) {
            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
            return;
        }

        // work out what we're on top of, first.
        List<KEntity> under = room.findEntitiesAtPoint(selectedTroll.getX() + 2, selectedTroll.getY() + 2);

        boolean foundSand = false;
        boolean foundWall = false;
        boolean foundTower = false;
        boolean foundGoldTower = false;

        for (KEntity e : under) {
            if (e instanceof Sand) foundSand = true;
            if (e instanceof Wall) foundWall = true;
            if (e instanceof Tower) foundTower = true;
            if (e instanceof GoldTower) foundGoldTower = true;
        }

        if (!foundSand) {
            // we are not in a suitable place for building
            selectedTroll.say("we got to build on sand!");
            return;
        }

        if (!foundWall) {
            buildWall();
            return;
        }

        if (!foundTower) {

            if (game.wallsBuilt < 16) {
                if (!DevHelper.skipWallRule) {
                    selectedTroll.say("[RED]build more walls!");
                    return;
                }
            }

            int x = selectedTroll.getX();
            int y = selectedTroll.getY();

            logger.info("Attempting to build tower at precise position " + x + "," + y);
            // 144,112
            // 208,112
            // 208,48
            // 144,48
            boolean allowedTowerLocation = false;
            if (x == 144 && y == 112) allowedTowerLocation = true;
            if (x == 208 && y == 112) allowedTowerLocation = true;
            if (x == 208 && y == 48) allowedTowerLocation = true;
            if (x == 144 && y == 48) allowedTowerLocation = true;

            if (!allowedTowerLocation) {
                selectedTroll.say("[RED]no build tower here!");
                return;
            }

            if (game.stone < 10) {
                selectedTroll.say("[RED]need 10 stone!");
                return;
            }
            game.stone -= 10;
            Tower tower = new Tower();
            tower.setX(selectedTroll.getX());
            tower.setY(selectedTroll.getY());
            room.addNewEntity(tower);
            game.build.play();
            selectedTroll.say("built tower!");
            return;
        }

        if (!foundGoldTower) {

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
            game.build.play();
            selectedTroll.say("built gold tower!");
            return;
        }

        selectedTroll.say("[RED]is gold tower already!");

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
        game.build.play();

    }
}
