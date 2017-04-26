package ld38;

import katsu.KEntity;
import katsu.KInputProcessor;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shaun on 26/04/2017.
 */
public class TrollManager extends KInputProcessor {

    private Troll selectedTroll = null;
    private TrollCastleGame game;
    private Map room;

    Logger logger = LoggerFactory.getLogger(TrollManager.class);
    private boolean hasSpawnedHint = false;


    public TrollManager(TrollCastleGame game, Map room) {
        this.game = game;
        this.room = room;
    }

    public void update() {

        // Handle trolls dying
        if (selectedTroll != null) {
            if (selectedTroll.isDestroyed()) {
                selectedTroll = null;
            }
        }

    }

    @Override
    public boolean keyTyped(char character) {

        if (character == 'x') {
            if (selectedTroll != null) {
                selectedTroll.destroy();
            }
        }
        return false;
    }

    private void handleBuildCommand() {
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
            if (game.stone < 3) {
                selectedTroll.say("need 3 stone.");
                return;
            }
            game.stone -= 3;
            Wall wall = new Wall();
            wall.setX(selectedTroll.getX());
            wall.setY(selectedTroll.getY());
            room.addNewEntity(wall);
            selectedTroll.say("built wall.");
            game.build.play();
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


    public void handleSpaceCommand() {

        if (selectedTroll == null) {

            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");

        } else {

            List<KEntity> under = room.findEntitiesAtPoint(selectedTroll.getX() + 2, selectedTroll.getY() + 2);
            KEntity highest = null;
            for (KEntity u : under) {
                if (!(u instanceof Troll)) {
                    highest = u;
                }
            }

            if (highest != null) {
                if (highest.getX() != selectedTroll.getX() || highest.getY() != selectedTroll.getY()) {
                    //selectedTroll.say("yerg " + highest.toString() + "pug.");
                }
            }

            if (highest instanceof Mushroom) {
                selectedTroll.setPsychedelic(true);
                game.hasEatenMushroom = true;
                highest.destroy();
                game.psych.play();
            }

            if (highest instanceof Mine) {
                selectedTroll.mine();
            }

            if (highest instanceof Mud) {
                BabyMushroom babyMushroom = new BabyMushroom();
                babyMushroom.setX(selectedTroll.getX());
                babyMushroom.setY(selectedTroll.getY());
                room.addNewEntity(babyMushroom);
                selectedTroll.say("mushroom planted!");
                game.plant.play();
            }

            if (highest instanceof Fish) {
                selectedTroll.hadFish = true;
                selectedTroll.say("yum yum fish!");
                selectedTroll.timesMined = 0;
                game.fish.play();
                highest.destroy();
                final int x = highest.getX();
                final int y = highest.getY();
                game.task(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Thread.sleep(30000);
                        Fish fish = new Fish();
                        fish.setX(x);
                        fish.setY(y);
                        room.addNewEntity(fish);
                        return true;
                    }
                });
            }

            if (highest instanceof Sand ||
                    highest instanceof Wall ||
                    highest instanceof BaseTower
                    ) {
                handleBuildCommand();
            }

        }

    }

    public void entityClicked(KEntity clickedEntity) {

        if (clickedEntity instanceof Troll) {

            // After we start selecting stuff, set a reminder about clicking everything else in the map.
            if (!hasSpawnedHint) {
                hasSpawnedHint = true;
                game.task(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Thread.sleep(30000);
                        game.ui.bottomBar.writeLine("[MAGENTA]Don't forget, you can click things to find out what they are!");
                        game.ui.bottomBar.writeLine("(Deselect any Trolls first by clicking them).");
                        return true;
                    }
                });
            }

            Troll clickedTroll = (Troll) clickedEntity;
            logger.info("Clicked Troll " + clickedTroll.toString());
            if (clickedTroll != selectedTroll) {
                // deselect existing if we have it
                if (selectedTroll != null) {
                    selectedTroll.getAppearance().setTextureFrom(Troll.class);
                }
                logger.info("Selecting Troll " + clickedTroll.toString());
                clickedTroll.onClick();
                selectedTroll = clickedTroll;
                selectedTroll.getAppearance().setTextureFrom(SelectedTroll.class);
            } else {
                // it's already selected, so deselect it
                logger.info("Deselecting Troll " + clickedTroll.toString());
                clickedTroll.getAppearance().setTextureFrom(Troll.class);
                selectedTroll = null;
            }

        }

        if (selectedTroll != null) {
            if (clickedEntity != null) {
                if (clickedEntity != selectedTroll) {
                    selectedTroll.setTargetEntity(clickedEntity);
                    if ((clickedEntity instanceof Fish || clickedEntity instanceof Water) && !selectedTroll.hasHadPsychedelics) {
                        selectedTroll.say("[RED]Me want fish. Me scared of water. Mushroom stop me being scared.");
                    } else {
                        selectedTroll.say("i go " + clickedEntity.toString());
                    }
                }
            }
        }
    }
}
