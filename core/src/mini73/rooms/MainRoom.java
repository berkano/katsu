package mini73.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import katsu.*;
import mini73.*;
import mini73.entities.base.FriendlyMob;
import mini73.entities.base.Resource;
import mini73.entities.mobs.*;
import mini73.entities.resources.Fuel;
import mini73.entities.resources.Iron;
import mini73.entities.resources.Potato;
import mini73.entities.resources.Robot;
import mini73.entities.structures.LandingPad;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public class MainRoom extends KRoom {

    private int speedFactor = 1;
    private ArrayList<View> views = new ArrayList<View>();
    public GameState gameState = new GameState(this);

    public boolean gameOver = false;

    public boolean objectiveReached = false;
    public boolean objectiveFailed = false;
    public KEntity selectedEntity;
    public KEntity player;
    public KEntity ship;

    public boolean firstUpdate = true;

    public boolean planetView = false;

    // TODO-LD28 Flexible inventory/resources system
    public int zoom = 1;

    public boolean inCommand = false;
    public Objective objectiveToAssign = Objective.NOTHING;

    public boolean shownWelcomeForLevel = false;
    public View mainView = new View();

    public TeleportMap teleportMap = TeleportMap.instance();

    Radar radar = new Radar();

    public void setZoom(int newZoom) {
        zoom = newZoom;
        mainView.portWidth = 1024 / zoom;
        mainView.portHeight = 768 / zoom;
    }

    @Override
    public void start() {

        super.start();

        mainView.screenWidth = 1024;
        mainView.screenHeight = 768;
        setZoom(2);
        mainView.portX = 0;
        mainView.portY = 0;

        loadFromTiledMap("debug-map");
        teleportMap.populateFrom(getEntities());

        player = findFirstEntity(PlayerPerson.class);
        ship = findFirstEntity(Ship.class);

        if (ship != null) {
            mainView.following = ship;
        }

        views.add(mainView);
        speedFactor = 1;

    }

    private KEntity findFirstEntity(Class clazz) {
        for (KEntity e : getEntities()) {
            if (clazz.isInstance(e)) return e;
        }
        return null;
    }

    @Override
    public void update() {

        bringEntitiesToFront(PlayerPerson.class);
        bringEntitiesToFront(Ship.class);

        super.update();

        if (K.runner.gamePaused()) return;

        Sounds.playAmbientMusicRandomly();

        if (gameState.fuel < 0) {
            gameState.fuel = 0;
        }

        if (firstUpdate) {
            firstUpdate = false;

            player.setX(teleportMap.findByName("Ship Helm").x * K.settings.getGridSize());
            player.setY(teleportMap.findByName("Ship Helm").y * K.settings.getGridSize());
            ship.setX(teleportMap.findByName("Xorx").x * K.settings.getGridSize());
            ship.setY(teleportMap.findByName("Xorx").y * K.settings.getGridSize());

        }


        LandingPad shipLandingPad = (LandingPad) ship.getInstanceUnderneath(LandingPad.class);
        LandingPad playerLandingPad = (LandingPad) player.getInstanceUnderneath(LandingPad.class);
        if (shipLandingPad != null) {
            if (playerLandingPad != null) {

                if (playerLandingPad.teleport.name.equals("Ship Helm")) {
                    if (shipLandingPad.teleport != null) {
                        Teleport linkedSurfaceTeleport = shipLandingPad.teleport.link;
                        playerLandingPad.teleport.link = linkedSurfaceTeleport;
                        if (linkedSurfaceTeleport != null) {
                            linkedSurfaceTeleport.link = playerLandingPad.teleport;
                        }
                    }
                }


            }
        } else {
            if (playerLandingPad != null) {
                playerLandingPad.teleport.link = null;
            }
        }

        if (K.input.wasKeyTyped(Keys.NUM_1)) tryTrade(1);
        if (K.input.wasKeyTyped(Keys.NUM_2)) tryTrade(2);
        if (K.input.wasKeyTyped(Keys.NUM_3)) tryTrade(3);
        if (K.input.wasKeyTyped(Keys.NUM_4)) tryTrade(4);
        if (K.input.wasKeyTyped(Keys.NUM_5)) tryTrade(5);
        if (K.input.wasKeyTyped(Keys.NUM_6)) tryTrade(6);
        if (K.input.wasKeyTyped(Keys.NUM_7)) tryTrade(7);
        if (K.input.wasKeyTyped(Keys.NUM_8)) tryTrade(8);
        if (K.input.wasKeyTyped(Keys.NUM_9)) tryTrade(9);

        if (K.input.wasKeyTyped(Keys.T)) {
            if (playerLandingPad != null) {
                if (playerLandingPad.teleport.link != null) {
                    planetClicked();
                    player.setX(playerLandingPad.teleport.link.x * K.settings.getGridSize());
                    player.setY(playerLandingPad.teleport.link.y * K.settings.getGridSize());
                    Sounds.transport.play();
                    playerLandingPad.teleport.link.discovered = true;
                    playerLandingPad.teleport.discovered = true;
                    if (shipLandingPad != null) {
                        shipLandingPad.teleport.discovered = true;
                    }
                    K.obsolete.ui.writeText("Teleported to " + playerLandingPad.teleport.link.getDiscoveredName() + ".");
                    if (playerLandingPad.teleport.link.getDiscoveredName().equals("London Surface")) {
                        Sounds.transport.stop();
                        Sounds.stopAllMusic();
                        Sounds.win.play();
                        K.obsolete.ui.writeText("WIN!! You found your way back to Earth :-) Hope you enjoyed playing. ~berkano / LD28");
                    }

                } else {
                    K.obsolete.ui.writeText("Cannot teleport until ship is docked.");
                }
            } else {
                K.obsolete.ui.writeText("You are not standing on a teleport.");
            }
        }


        // TODO-LD28
        if (!shownWelcomeForLevel) {
//            if (Katsu.game.currentLevel == "0000") {
            K.obsolete.ui.writeText("welcome to ~singleton~, berkano's LD28 entry.");
            K.obsolete.ui.writeText("major tim is lost in space. one prisoner, one");
            K.obsolete.ui.writeText("sheep, one inventory slot, one goal:");
            K.obsolete.ui.writeText("help him find his way back to earth");
            K.obsolete.ui.writeText("press h for help.");
//            }
            shownWelcomeForLevel = true;
        }

        if (!objectiveReached && !objectiveFailed) checkLevelCompleteCriteria();

        if (Gdx.input.isKeyPressed(Keys.F3)) {
            K.obsolete.ui.writeText("entities=" + String.valueOf(getEntities().size()));
        }

        if (K.input.wasKeyTyped(Keys.I)) {
            String result = runInventoryRules();
            K.obsolete.ui.writeText(result);
        }


        if (K.input.wasKeyTyped(Keys.SPACE)) {
            if (selectedEntity != null) {
                if (selectedEntity instanceof Resource) {
                    selectedEntity.destroy();
                    if (selectedEntity instanceof Fuel) {
                        gameState.fuel += 1000;
                    }
                    if (selectedEntity instanceof Potato) {
                        player.addHealth(25);
                        player.capHealth(100);
                    }
                    if (selectedEntity instanceof Iron) {
                        ship.addHealth(25);
                        ship.capHealth(100);
                    }
                }
            }
        }

        checkInputAndMovePlayer(Keys.W, 0, -1);
        checkInputAndMovePlayer(Keys.A, -1, 0);
        checkInputAndMovePlayer(Keys.S, 0, 1);
        checkInputAndMovePlayer(Keys.D, 1, 0);


        if (K.input.wasKeyTyped(Keys.X)) {
            if (K.settings.isDevMode()) {
                Teleport winTP = teleportMap.findByName("Earth");
                ship.setX(winTP.x * 16);
                ship.setY(winTP.y * 16);
                if (K.settings.isDevMode()) {
                objectiveReached = true;
                }
            }
        }

        if (K.input.wasKeyTyped(Keys.U)) {
            if (planetView) {
                universeClicked();
            } else {
                planetClicked();
            }
        }

        if (K.input.wasKeyTyped(Keys.Z)) {
            doZoom();
        }
    }


    public void bringEntitiesToFront(Class filterClass) {

        ArrayList<KEntity> onTopEntities = new ArrayList<KEntity>();

        // Sort components to always be on top
        for (KEntity e : getEntities()) {
            if (filterClass.isInstance(e)) onTopEntities.add(e);
        }

        for (KEntity e : onTopEntities) {
            getEntities().remove(e);
            getEntities().add(e);
        }

    }

    private void tryTrade(int i) {
        if (selectedEntity instanceof FriendlyPerson) {
            ((FriendlyPerson) selectedEntity).tryTrade(i);
        } else {
            K.obsolete.ui.writeText("Click on a friendly person before trading!");
        }

    }

    private String runInventoryRules() {

        if (selectedEntity != null) {

            // distance check
            Object playerObj = findNearest(selectedEntity.getX(), selectedEntity.getY(), PlayerPerson.class, 80, null);
            Object shipObj = findNearest(selectedEntity.getX(), selectedEntity.getY(), Ship.class, 80, null);

            if (playerObj == null && shipObj == null) {
                return ("Player or ship must be within 5 blocks!");
            }

            if (selectedEntity instanceof Resource) {
                if (gameState.inventory == null) {
                    selectedEntity.destroy();
                    gameState.inventory = selectedEntity.getClass();
                    return ("Picked up " + selectedEntity.toString());
                } else {
                    return ("Inventory full!");
                }
            } else {
                if (gameState.inventory != null) {
                    try {
                        KEntity e = (KEntity) gameState.inventory.newInstance();
                        gameState.inventory = null;
                        e.setPosition(selectedEntity);
                        addNewEntity(e);
                        return ("Dropped " + e.toString());
                    } catch (Exception ex) {
                        return ("Exception " + ex.toString());
                    }
                } else {
                    return ("Inventory empty!");
                }
            }
        } else {
            return ("No item selected!");
        }

    }

    private KEntity findNearest(int x, int y, Class clazz, int i, Object o) {
        UnfinishedBusinessException.raise();
        return null;
    }

    private void checkInputAndMovePlayer(int keyCode, int dx, int dy) {

        int moveInterval = 100;
        if (!planetView) moveInterval = 25;
        long lastMovedMillis = System.currentTimeMillis() - mainView.following.getLastMove();
        if (lastMovedMillis < moveInterval) return;
        if (!K.input.isKeyDown(keyCode)) return;

        if (mainView.following instanceof Ship) {
            if (player.isOnTopOf(LandingPad.class)) {
            } else {
                K.obsolete.ui.writeText("May not move ship until player is at helm.");
                return;
            }
            if (gameState.fuel == 0) {
                K.obsolete.ui.writeText("Out of fuel!");
                return;
            }
            player.setX(46 * K.settings.getGridSize());
            player.setY(36 * K.settings.getGridSize());

        }

        mainView.following.moveRelative(dx, dy);
    }

    protected void rawScreenClick(int x, int y) {
        K.obsolete.ui.writeText(String.format("Clicked top bar at %s, %s", x, y));

        if (x >= 0 && x < 105) {
            newGameClicked();
        }
        if (x >= 105 && x < 170) {
//            helpClicked();
        }
        if (x >= 170 && x < 283) {
            universeClicked();
        }
        if (x >= 283 && x < 367) {
            planetClicked();
        }
        if (x >= 367 && x < 524) {
            landLaunchClicked();
        }
        if (x >= 524 && x < 676) {
            leaveEnterClicked();
        }
        if (x >= 676 && x < 749) {
            zoomClicked();
        }
    }

    private void zoomClicked() {
        doZoom();
    }

    private void doZoom() {

        switch (zoom) {
            case 4:
                setZoom(1);
                break;
            case 2:
                setZoom(4);
                break;
            case 1:
                setZoom(2);
                break;
        }


    }

    private void leaveEnterClicked() {
        K.obsolete.ui.writeText("leave/enter...");
    }

    private void landLaunchClicked() {
        K.obsolete.ui.writeText("land/launch...");
    }

    private void planetClicked() {
        if (!planetView) {
            //ui.writeText("Planet View");
            planetView = true;
            mainView.following = player;
        }
    }

    private void universeClicked() {
        //ui.writeText("Universe Map");
        if (planetView) {
            planetView = false;
            mainView.following = ship;
        }
    }

    private void newGameClicked() {
        K.obsolete.ui.writeText("Generating universe");
    }


    private void tryAssignObjective(Objective obj) {

        if (selectedEntity == null) return;

        if (!(selectedEntity instanceof FriendlyMob)) {
            K.obsolete.ui.writeText(selectedEntity.toString() + " ignored you.");
            return;
        }

        if (obj == Objective.NOTHING) {
            selectedEntity.setCurrentObjective(obj);
            return;
        }

        inCommand = true;
        objectiveToAssign = obj;
    }

    private void checkLevelCompleteCriteria() {

    }

    @Override
    public void render() {


        super.render();

        String info;

        // Render navigation bar
        //info = "New game | Help | Universe | Planet | Land/Launch | Leave/Enter | Zoom |";
        //ui.drawStringAbsolute(info, Color.BLACK, 2, 2, batch);
        //ui.drawStringAbsolute(info, Color.ORANGE, 0, 2, batch);

        // TODO-LD28
        info = gameState.statusBar();

        K.obsolete.ui.drawStringAbsolute(info, Color.BLACK, 2, 768);
        K.obsolete.ui.drawStringAbsolute(info, Color.WHITE, 0, 768);

        if (K.settings.isLogFPS()) {
            String fps = String.valueOf(Gdx.graphics.getFramesPerSecond());
            info = "FPS: " + fps;
            K.obsolete.ui.drawStringAbsolute(info, Color.BLACK, 2, 768 - (32 + 18));
            K.obsolete.ui.drawStringAbsolute(info, Color.CYAN, 0, 768 - (32 + 16));
        }

        if (K.runner.gamePaused()) {

            info = "GAME PAUSED - PRESS P TO RESUME";
            K.obsolete.ui.drawStringAbsolute(info, Color.BLACK, 102, 768 - 118);
            K.obsolete.ui.drawStringAbsolute(info, Color.CYAN, 100, 768 - 116);

        }

//        K.obsolete.ui.drawStringAbsolute(game.pinCode, Color.DARK_GRAY, 1024 - 48, 0, batch);

    }

//    @Override
    public void onClick(int roomX, int roomY, boolean leftClicking, boolean rightClicking) {
//        super.onClick(roomX, roomY, leftClicking, rightClicking);

        ArrayList<KEntity> clickedEntities = findEntitiesAtPoint(roomX, roomY);

        if (clickedEntities.size() >= 1) {
            KEntity e = clickedEntities.get(clickedEntities.size() - 1);

            if (rightClicking) {
                if (e != null) {
                    mainView.following = e;
                }
            }

            if (leftClicking) {
                if (e != null) {

                    boolean playedSelectSound = false;

                    if (e instanceof FriendlyMob) {
                        //playedSelectSound = true;
                    }

                    if (inCommand) {
                        selectedEntity.setCurrentObjective(objectiveToAssign);
                        selectedEntity.say("Command accepted");
                        selectedEntity.setTargetEntity(e);
                        objectiveToAssign = Objective.NOTHING;
                        inCommand = false;
                    } else {
                        if (selectedEntity != null) {
                            selectedEntity.setSelected(false);
                        }
                        selectedEntity = e;
                        selectedEntity.setSelected(true);
                        if (!playedSelectSound) {
                            boolean playedCustomSound = false;
                            if (selectedEntity instanceof Robot) {
                                Sounds.robotspeak.play();
                                playedCustomSound = true;
                            }
                            if (selectedEntity instanceof Ship) {
                                Sounds.shipspeak.play();
                                playedCustomSound = true;
                            }
                            if (selectedEntity instanceof Sheep) {
                                Sounds.sheep.play();
                                playedCustomSound = true;
                            }
                            if (selectedEntity instanceof FriendlyPerson) {
                                Sounds.friendlyspeak.play();
                                playedCustomSound = true;
                            }
                            if (selectedEntity instanceof FriendlyShip) {
                                Sounds.friendlyshipspeak.play();
                                playedCustomSound = true;
                            }
                            if (selectedEntity instanceof EnemyShip) {
                                Sounds.enemyshipspeak.play();
                                playedCustomSound = true;
                            }
                            if (selectedEntity instanceof EnemyPerson) {
                                Sounds.enemypersonspeak.play();
                                playedCustomSound = true;
                            }

                            if (!playedCustomSound) {
                                Sounds.select.play();
                            }
                        }
                    }
                } else {
                    selectedEntity = null;
                }
            }

        }

    }

    public void preRender() {
        StarField.render();
        if (!planetView) {
            radar.render();
        }

    }
}
