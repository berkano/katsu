package ld28.rooms;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import katsu.*;
import ld28.*;
import ld28.entities.base.FriendlyMob;
import ld28.entities.base.Resource;
import ld28.entities.mobs.*;
import ld28.entities.resources.Fuel;
import ld28.entities.resources.Iron;
import ld28.entities.resources.Potato;
import ld28.entities.resources.Robot;
import ld28.entities.structures.LandingPad;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
public class MainRoom extends Room {

    public GameState gameState = new GameState(this);

    public View mainView = new View(this);
    public boolean gameOver = false;

    public boolean objectiveReached = false;
    public boolean objectiveFailed = false;
    public Entity selectedEntity;
    public Entity player;
    public Entity ship;

    public boolean firstUpdate = true;

    public boolean planetView = false;

    // TODO-LD28 Flexible inventory/resources system
    public int zoom = 1;

    public boolean inCommand = false;
    public Objective objectiveToAssign = Objective.NOTHING;

    public boolean shownWelcomeForLevel = false;

    public void setZoom(int newZoom) {
        zoom = newZoom;
        if (DevTools.superZoomOut) {
            DevTools.todo("Move away from fixed screen res");
           mainView.portWidth = 1024*4;
            mainView.portHeight = 768*4;
        } else {
            mainView.portWidth = 1024 / zoom;
            mainView.portHeight = 768 / zoom;
        }
    }

    public MainRoom(String tmx) {

        mainView.screenWidth = 1024;
        mainView.screenHeight = 768;
        setZoom(2);
        mainView.portX = 0;
        mainView.portY = 0;

        Util.loadFromTMX(this, tmx);

        TeleportMap.populateFrom(entities);

        player = findFirst(PlayerPerson.class);
        ship = findFirst(Ship.class);

        if (ship != null) {
            mainView.following = ship;
        }

        views.add(mainView);
        speedFactor = 1;

        // TODO-LD28
        //Util.stopAll(null);

        if (Katsu.game.musicEnabled) {
            // TODO-LD28
            // Util.playOneOf(null);
        }

    }

    @Override
    public void update(Application gc) {

        bringEntitiesToFront(PlayerPerson.class);
        bringEntitiesToFront(Ship.class);

        super.update(gc);

        if (game.paused) return;

        Sounds.playAmbientMusicRandomly();

        if (gameState.fuel < 0) {
            gameState.fuel = 0;
        }

        if (firstUpdate) {
            firstUpdate = false;

            player.x = TeleportMap.findByName("Ship Helm").x * Settings.tileWidth;
            player.y = TeleportMap.findByName("Ship Helm").y * Settings.tileHeight;
            ship.x = TeleportMap.findByName("Xorx").x * Settings.tileWidth;
            ship.y = TeleportMap.findByName("Xorx").y * Settings.tileHeight;

        }

        teleportLogic();

        tradingLogic();

        if (!shownWelcomeForLevel) {
            if (Katsu.game.currentLevel == "0000") {
                ui.writeText("welcome to ~singleton~, berkano's LD28 entry.");
                ui.writeText("major tim is lost in space. one prisoner, one");
                ui.writeText("sheep, one inventory slot, one goal:");
                ui.writeText("help him find his way back to earth");
                ui.writeText("press h for help.");
            }
            shownWelcomeForLevel = true;
        }

        if (!objectiveReached && !objectiveFailed) checkLevelCompleteCriteria();

        if (Gdx.input.isKeyPressed(Keys.F3)) {
            ui.writeText("entities=" + String.valueOf(entities.size()));
        }

        if (Katsu.game.isKeyTyped(Keys.I)) {
            String result = runInventoryRules();
            ui.writeText(result);
        }

        if (Katsu.game.isKeyTyped(Keys.SPACE)) {
            useItemLogic();
        }


        movePlayerLogic();

        if (Katsu.game.isKeyTyped(Keys.X)) {
            if (Settings.devMode) {
                Teleport winTP = TeleportMap.findByName("Earth");
                ship.x = winTP.x * 16;
                ship.y = winTP.y * 16;
            }
        }

        if (Katsu.game.isKeyTyped(Keys.U)) {
            if (planetView) {
                universeClicked();
            } else {
                planetClicked();
            }
        }

        if (Katsu.game.isKeyTyped(Keys.SPACE)) {
            if (objectiveReached) {
                String nextLevel = LevelManager.nextLevel(Katsu.game.currentLevel);
                if (nextLevel != "") {
                    Katsu.game.startLevel(nextLevel);
                }
            }
        }


        if (Katsu.game.isKeyTyped(Keys.Z)) {
            doZoom();
        }
    }

    private void movePlayerLogic() {
        checkInputAndMovePlayer(Keys.W, 0, -1);
        checkInputAndMovePlayer(Keys.A, -1, 0);
        checkInputAndMovePlayer(Keys.S, 0, 1);
        checkInputAndMovePlayer(Keys.D, 1, 0);
    }

    private void useItemLogic() {
        if (selectedEntity != null) {
            if (selectedEntity instanceof Resource) {
                selectedEntity.wantsDestroy = true;
                if (selectedEntity instanceof Fuel) {
                    gameState.fuel += GameParameters.refuelAmount;
                }
                if (selectedEntity instanceof Potato) {
                    player.health += GameParameters.potatoGiveHealthAmount;
                    if (player.health > 100) player.health = 100;
                }
                if (selectedEntity instanceof Iron) {
                    ship.health += GameParameters.ironGiveRepairAmount;
                    if (ship.health > 100) ship.health = 100;
                }
            }
        }
    }

    private void teleportLogic() {
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


        if (Katsu.game.isKeyTyped(Keys.T)) {
            if (playerLandingPad != null) {
                if (playerLandingPad.teleport.link != null) {
                    planetClicked();
                    player.x = playerLandingPad.teleport.link.x * Settings.tileWidth;
                    player.y = playerLandingPad.teleport.link.y * Settings.tileWidth;
                    Sounds.transport.play();
                    playerLandingPad.teleport.link.discovered = true;
                    playerLandingPad.teleport.discovered = true;
                    if (shipLandingPad != null) {
                        shipLandingPad.teleport.discovered = true;
                    }
                    ui.writeText("Teleported to " + playerLandingPad.teleport.link.getDiscoveredName() + ".");
                    if (playerLandingPad.teleport.link.getDiscoveredName().equals("London Surface")) {
                        Sounds.transport.stop();
                        Sounds.stopAllMusic();
                        Sounds.win.play();
                        ui.writeText("WIN!! You found your way back to Earth :-) Hope you enjoyed playing. ~berkano / LD28");
                    }

                } else {
                    ui.writeText(Messages.CANNOT_TELEPORT_UNTIL_SHIP_IS_DOCKED);
                }
            } else {
                ui.writeText(Messages.YOU_ARE_NOT_STANDING_ON_A_TELEPORT);
            }
        }
    }

    private void tradingLogic() {
        if (Katsu.game.isKeyTyped(Keys.NUM_1)) tryTrade(1);
        if (Katsu.game.isKeyTyped(Keys.NUM_2)) tryTrade(2);
        if (Katsu.game.isKeyTyped(Keys.NUM_3)) tryTrade(3);
        if (Katsu.game.isKeyTyped(Keys.NUM_4)) tryTrade(4);
        if (Katsu.game.isKeyTyped(Keys.NUM_5)) tryTrade(5);
        if (Katsu.game.isKeyTyped(Keys.NUM_6)) tryTrade(6);
        if (Katsu.game.isKeyTyped(Keys.NUM_7)) tryTrade(7);
        if (Katsu.game.isKeyTyped(Keys.NUM_8)) tryTrade(8);
        if (Katsu.game.isKeyTyped(Keys.NUM_9)) tryTrade(9);
    }

    private void tryTrade(int i) {
        if (selectedEntity instanceof FriendlyPerson) {
            ((FriendlyPerson) selectedEntity).tryTrade(i);
        } else {
            ui.writeText(Messages.CLICK_ON_A_FRIENDLY_PERSON_BEFORE_TRADING);
        }

    }

    private String runInventoryRules() {

        if (selectedEntity != null) {

            // distance check
            Object playerObj = findNearest(selectedEntity.x, selectedEntity.y, PlayerPerson.class, 80, null);
            Object shipObj = findNearest(selectedEntity.x, selectedEntity.y, Ship.class, 80, null);

            if (playerObj == null && shipObj == null) {
                return ("Player or ship must be within 5 blocks!");
            }

            if (selectedEntity instanceof Resource) {
                if (gameState.inventory == null) {
                    selectedEntity.wantsDestroy = true;
                    gameState.inventory = selectedEntity.getClass();
                    return ("Picked up " + selectedEntity.toString());
                } else {
                    return ("Inventory full!");
                }
            } else {
                if (gameState.inventory != null) {
                    try {
                        Entity e = (Entity) gameState.inventory.newInstance();
                        gameState.inventory = null;
                        e.x = selectedEntity.x;
                        e.y = selectedEntity.y;
                        createList.add(e);
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

    private void checkInputAndMovePlayer(int keyCode, int dx, int dy) {

        dx = dx * DevTools.moveFactor;
        dy = dy * DevTools.moveFactor;

        int moveInterval = 100;
        if (!planetView) moveInterval = 25;
        long lastMovedMillis = System.currentTimeMillis() - mainView.following.lastMoved;
        if (lastMovedMillis < moveInterval) return;
        if (!Katsu.game.isKeyDown(keyCode)) return;

        if (mainView.following instanceof Ship) {
            if (player.isOnTopOf(LandingPad.class) || DevTools.freeMoveCheat) {
            } else {
                ui.writeText(Messages.MAY_NOT_MOVE_SHIP_UNTIL_PLAYER_IS_AT_HELM);
                return;
            }
            if (gameState.fuel == 0 && !DevTools.noFuelLimit) {
                ui.writeText(Messages.OUT_OF_FUEL);
                return;
            }
            // Move the player to the helm
            player.x = 46 * Settings.tileWidth;
            player.y = 36 * Settings.tileHeight;

        }

        mainView.following.moveRelative(dx, dy);
    }

    @Override
    protected void rawScreenClick(int x, int y) {
        ui.writeText(String.format("Clicked top bar at %s, %s", x, y));

        if (x >= 0 && x < 105) {
            newGameClicked();
        }
        if (x >= 105 && x < 170) {
            helpClicked();
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
        ui.writeText("leave/enter...");
    }

    private void landLaunchClicked() {
        ui.writeText("land/launch...");
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

    private void helpClicked() {
        game.doHelp();
    }

    private void newGameClicked() {
        ui.writeText("Generating universe");
    }


    private void tryAssignObjective(Objective obj) {

        if (selectedEntity == null) return;

        if (!(selectedEntity instanceof FriendlyMob)) {
            ui.writeText(selectedEntity.toString() + " ignored you.");
            return;
        }

        if (obj == Objective.NOTHING) {
            selectedEntity.currentObjective = obj;
            return;
        }

        inCommand = true;
        objectiveToAssign = obj;
    }

    private void checkLevelCompleteCriteria() {

    }

    @Override
    public void render(Graphics g, Application gc, SpriteBatch batch) {


        super.render(g, gc, batch);

        String info;

        // Render navigation bar
        //info = "New game | Help | Universe | Planet | Land/Launch | Leave/Enter | Zoom |";
        //ui.drawString(info, Color.BLACK, 2, 2, batch);
        //ui.drawString(info, Color.ORANGE, 0, 2, batch);

        // TODO-LD28
        info = gameState.statusBar();

        ui.drawString(info, Color.BLACK, 2, 2, batch);
        ui.drawString(info, Color.WHITE, 0, 2, batch);

        if (Settings.showFPS) {
            String fps = String.valueOf(Gdx.graphics.getFramesPerSecond());
            info = "FPS: " + fps + " PF:" + Game.pathFinds;
            ui.drawString(info, Color.BLACK, 2, 32 + 18, batch);
            ui.drawString(info, Color.CYAN, 0, 32 + 16, batch);
        }

        if (objectiveReached) {
            info = "LEVEL CLEARED! Press SPACE for next level";
            ui.drawString(info, Color.BLACK, 2, 18, batch);
            ui.drawString(info, Color.GREEN, 0, 16, batch);
        }
        if (objectiveFailed) {
            info = "LEVEL FAILED :-( Press R to restart";
            ui.drawString(info, Color.BLACK, 2, 18, batch);
            ui.drawString(info, Color.RED, 0, 16, batch);
        }
        if (Katsu.game.paused) {

            info = "GAME PAUSED - PRESS P TO RESUME";
            ui.drawString(info, Color.BLACK, 102, 118, batch);
            ui.drawString(info, Color.CYAN, 100, 116, batch);

        }

        ui.drawString(game.pinCode, Color.DARK_GRAY, 1024 - 48, 0, batch);

        if (selectedEntity != null) {

        }


    }

    @Override
    public void onClick(int roomX, int roomY, boolean leftClicking, boolean rightClicking) {
        super.onClick(roomX, roomY, leftClicking, rightClicking);

        ArrayList<Entity> clickedEntities = findEntitiesAtPoint(roomX, roomY);

        if (clickedEntities.size() >= 1) {
            Entity e = clickedEntities.get(clickedEntities.size() - 1);

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
                        selectedEntity.currentObjective = objectiveToAssign;
                        selectedEntity.say("Command accepted");
                        selectedEntity.targetEntity = e;
                        objectiveToAssign = Objective.NOTHING;
                        inCommand = false;
                    } else {
                        if (selectedEntity != null) {
                            selectedEntity.selected = false;
                        }
                        selectedEntity = e;
                        selectedEntity.selected = true;
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
                                sounds.select.play();
                            }
                        }
                    }
                } else {
                    selectedEntity = null;
                }
            }

        }

    }

    @Override
    public void preRender() {
        StarField.render();
        if (!planetView) {
            Radar.render();
        }

    }
}
