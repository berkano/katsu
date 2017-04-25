package ld38;

import com.badlogic.gdx.math.Vector3;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {

    private int lastDragX = 0;
    private int lastDragY = 0;
    private boolean hasDragged = false;

    private Logger logger = LoggerFactory.getLogger(Map.class);

    private TrollCastleGame game;

    private Troll selectedTroll = null;

    boolean hasSpawnedHint = false;

    @Override
    public void update() {
        super.update();

        // Handle trolls dying
        if (selectedTroll != null) {
            if (selectedTroll.isDestroyed()) {
                selectedTroll = null;
            }
        }

        game.trolls = 0;
        game.wallsBuilt = 0;
        game.goldTowersBuilt = 0;
        game.towersBuilt = 0;
        List<KEntity> trolls = new ArrayList<>();
        List<KEntity> tubes = new ArrayList<>();
        List<KEntity> towers = new ArrayList<>();
        for (KEntity e: getEntities()) {
            if (e instanceof Troll) {
                Troll t = (Troll) e;
                trolls.add(t);
                game.trolls++;
            }
            if (e instanceof SwimTube) {
                tubes.add(e);
            }
            if (e instanceof BaseTower) {
                towers.add(e);
            }
            if (e instanceof Wall) {
                game.wallsBuilt++;
            }
            if (e instanceof Tower) {
                game.towersBuilt++;
            }
            if (e instanceof GoldTower) {
                game.goldTowersBuilt++;
            }
        }

        // z order - towers
        for (KEntity e: towers) {
            getEntities().remove(e);
            getEntities().add(e);
        }

        // z order - trolls
        for (KEntity e: trolls) {
            getEntities().remove(e);
            getEntities().add(e);
        }

        // z order - tubes
        for (KEntity e: tubes) {
            getEntities().remove(e);
            getEntities().add(e);
        }

    }

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("map");
        setupCamera();
        game = TrollCastleGame.instance();

        if (DevHelper.randomMushroomOnStart) {
            Mushroom mushroom = new Mushroom();
            mushroom.setX(128);
            mushroom.setY(128);
            addNewEntity(mushroom);
        }


    }

    private void setupCamera() {
        int centreX = 32 * 4;
        int centreY = 32 * 4;
        K.graphics.camera.zoom = 0.25f;
        K.graphics.camera.position.x = centreX;
        K.graphics.camera.position.y = centreY;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        hasDragged = false;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        if (character == 'h') {
            game.ui.toggleHelp();
        }
        if (character == ' ') {
            handleSpaceCommand();
        }
        if (character == 'x') {
            if (selectedTroll != null) {
                selectedTroll.destroy();
            }
        }
        return false;
    }

    @Override
    public ArrayList<KEntity> findEntitiesAtPoint(int x, int y) {
        ArrayList<KEntity> result = super.findEntitiesAtPoint(x, y);
        ArrayList<KEntity> filtered = new ArrayList<>();
        for (KEntity e : result) {
            if (!(e instanceof SwimTube)) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    private void handleBuildCommand() {
        // do we have a selected troll?
        if (selectedTroll == null) {
            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
            return;
        }

        // work out what we're on top of, first.
        List<KEntity> under = findEntitiesAtPoint(selectedTroll.getX() + 2, selectedTroll.getY() + 2);


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
            addNewEntity(wall);
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

            logger.info("Attempting to build tower at precise position "+x+","+y);
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
            addNewEntity(tower);
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
            addNewEntity(tower);
            game.build.play();
            selectedTroll.say("built gold tower!");
            return;
        }

        selectedTroll.say("[RED]is gold tower already!");

    }

    private void handleSpaceCommand() {

        if (selectedTroll == null) {

            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");

        } else {

            List<KEntity> under = findEntitiesAtPoint(selectedTroll.getX() + 2, selectedTroll.getY() + 2);
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
                addNewEntity(babyMushroom);
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
                        addNewEntity(fish);
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

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        logger.info("Detected touch up");

        if (hasDragged) return false;

        KEntity clickedEntity = null;

        List<KEntity> entitiesUnderClick = entitiesAtScreenPoint(screenX, screenY);

        Troll clickedTroll;

        // Iterate thru entities, topmost last
        for (KEntity e : entitiesUnderClick) {
            logger.info("calling onClick for an instance of " + e.getClass().getSimpleName());
            clickedEntity = e;
        }

        // Troll select / deselect
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

            clickedTroll = (Troll)clickedEntity;
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
        } else {
            if (clickedEntity != null) {
                logger.info("Calling onClick for entity " + clickedEntity.toString());
                clickedEntity.onClick();
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        hasDragged = true;

        int dx = screenX - lastDragX;
        int dy = screenY - lastDragY;

        logger.info("detected drag at " + screenX + "," + screenY + " with delta " + dx + "," + dy);

        K.graphics.camera.position.x -= dx * K.graphics.camera.zoom;
        K.graphics.camera.position.y += dy * K.graphics.camera.zoom;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
    }

    public void makeWaterPassable() {
        for (KEntity e : getEntities()) {
            if (e instanceof Water) {
                e.setSolid(false);
            }
        }
    }

    public void makeWaterSolid() {
        for (KEntity e : getEntities()) {
            if (e instanceof Water) {
                e.setSolid(true);
            }
        }
    }
}
