package ld38;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Select;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        List<KEntity> towers = new ArrayList<>();
        for (KEntity e: getEntities()) {
            if (e instanceof Troll) {
                Troll t = (Troll) e;
                trolls.add(t);
                game.trolls++;
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

    }

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("map");
        setupCamera();
        game = TrollCastleGame.instance();
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
        return false;
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
            selectedTroll.say("ogg Sand digg mog.");
            return;
        }

        if (!foundWall) {
            if (game.stone < 10) {
                selectedTroll.say("nog 10 Stone gott.");
                return;
            }
            game.stone -= 10;
            Wall wall = new Wall();
            wall.setX(selectedTroll.getX());
            wall.setY(selectedTroll.getY());
            addNewEntity(wall);
            selectedTroll.say("Wall bilded!");
            game.build.play();
            return;
        }

        if (!foundTower) {

            if (game.wallsBuilt < 16) {
                if (!DevHelper.skipWallRule) {
                    selectedTroll.say("[RED]moar Wall bog! moar Wall bog!");
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
                selectedTroll.say("[RED]nog Tower haar! nog Tower haar!");
                return;
            }

            if (game.stone < 20) {
                selectedTroll.say("[RED]nog 20 Stone gott.");
                return;
            }
            game.stone -= 20;
            Tower tower = new Tower();
            tower.setX(selectedTroll.getX());
            tower.setY(selectedTroll.getY());
            addNewEntity(tower);
            game.build.play();
            selectedTroll.say("Tower bilded!");
            return;
        }

        if (!foundGoldTower) {
            if (game.gold < 10) {
                selectedTroll.say("[RED]nog 10 Gold gott.");
                return;
            }
            game.gold -= 10;

            GoldTower tower = new GoldTower();
            tower.setX(selectedTroll.getX());
            tower.setY(selectedTroll.getY());
            addNewEntity(tower);
            game.build.play();
            selectedTroll.say("GoldTower bilded!");
            return;
        }

        selectedTroll.say("[RED]ogg GoldTower ag!");

    }

    private void handleSpaceCommand() {

        if (selectedTroll == null) {

            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");

        } else {

            List<KEntity> under = findEntitiesAtPoint(selectedTroll.getX(), selectedTroll.getY());
            KEntity highest = null;
            for (KEntity u : under) {
                if (!(u instanceof Troll)) {
                    highest = u;
                }
            }

            if (highest != null) {
                if (highest.getX() != selectedTroll.getX() || highest.getY() != selectedTroll.getY()) {
                    selectedTroll.say("yerg " + highest.toString() + "pug.");
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
                selectedTroll.say("Babby Mushroom plod!");
            }

            if (highest instanceof Fish) {
                selectedTroll.hadFish = true;
                selectedTroll.say("Blubby blubby!");
                selectedTroll.timesMined = 0;
                //highest.destroy();
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
                            selectedTroll.say("[GREEN]Luvluv Fish! [RED]Eek, Water! [CYAN]Nomnom [RED]M[WHITE]o[RED]o[WHITE]s[RED]h[WHITE]!!");
                    } else {
                        selectedTroll.say("ogg " + clickedEntity.toString() + " mog.");
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

    private List<KEntity> entitiesAtScreenPoint(int screenX, int screenY) {

        Vector3 clickLocation = new Vector3(screenX, screenY, 0);
        Vector3 worldLocation = K.graphics.camera.unproject(clickLocation);

        return findEntitiesAtPoint(Math.round(worldLocation.x), Math.round(worldLocation.y));

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
