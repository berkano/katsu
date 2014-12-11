package ld31v2;

import ext.pathfinding.grid.GridMap;
import ld31v2.entities.*;
import panko.*;

/**
 * Created by shaun on 06/12/2014.
 */
public class CampaignMap extends PankoRoomBase {

    private Selection selection;
    private static GridMap pathMap;
    private boolean p1lost = false;
    private boolean p2lost = false;
    private boolean p3lost = false;
    private boolean p4lost = false;
    private boolean p5lost = false;
    private boolean p6lost = false;
    private boolean gameWon = false;

    public static GridMap getPathMap() {
        return pathMap;
    }

    @Override
    public void update() {
        super.update();
        updatePathMap();
    }

    private void updatePathMap() {

        checkWinLoseState();

        pathMap = new GridMap(64, 48);

        for (PankoEntity e : getEntities()) {
            if (e.isSolid()) {
                // Assumes all entities are the same size as their tile
                int cellX = e.getX() / e.getWidth();
                int cellY = e.getY() / e.getHeight();

                pathMap.set(cellX, cellY, GridMap.WALL);
            }
        }
    }

    private void checkWinLoseState() {

        int mob1count = 0;
        int mob2count = 0;
        int mob3count = 0;
        int mob4count = 0;
        int mob5count = 0;
        int mob6count = 0;

        for (PankoEntity e : getEntities()) {
            if (e instanceof SoldierP1) mob1count++;
            if (e instanceof SoldierP2) mob2count++;
            if (e instanceof SoldierP3) mob3count++;
            if (e instanceof SoldierP4) mob4count++;
            if (e instanceof SoldierP5) mob5count++;
            if (e instanceof SoldierP6) mob6count++;
        }

        boolean needsPause = false;

        if (mob1count == 0) {
            if (!p1lost) {
                p1lost = true;
                Panko.getUI().writeText("@RED YOU ARE DEFEATED :-(");
                needsPause = true;
            }
        }

        if (mob2count == 0) {
            if (!p2lost) {
                p2lost = true;
                Panko.getUI().writeText("@RED Red is defeated!");
                needsPause = true;
            }
        }

        if (mob3count == 0) {
            if (!p3lost) {
                p3lost = true;
                Panko.getUI().writeText("@RED Purple is defeated!");
                needsPause = true;
            }
        }

        if (mob4count == 0) {
            if (!p4lost) {
                p4lost = true;
                Panko.getUI().writeText("@RED Green is defeated!");
                needsPause = true;
            }
        }

        if (mob5count == 0) {
            if (!p5lost) {
                p5lost = true;
                Panko.getUI().writeText("@RED Blue is defeated!");
                needsPause = true;
            }
        }

        if (mob6count == 0) {
            if (!p6lost) {
                p6lost = true;
                Panko.getUI().writeText("@RED Yellow is defeated!");
                needsPause = true;
            }
        }


        if (!gameWon) {
            if (!p1lost && p2lost && p3lost && p4lost && p5lost && p6lost) {
                Panko.getUI().writeText("@GREEN YOU ARE VICTORIOUS!");
                gameWon = true;
                needsPause = true;
            }
            if (!p2lost && p1lost && p3lost && p4lost && p5lost && p6lost) {
                Panko.getUI().writeText("@GREEN Red is victorious!");
                gameWon = true;
                needsPause = true;
            }
            if (!p3lost && p1lost && p2lost && p4lost && p5lost && p6lost) {
                Panko.getUI().writeText("@GREEN Purple is victorious!");
                gameWon = true;
                needsPause = true;
            }
            if (!p4lost && p1lost && p2lost && p3lost && p5lost && p6lost) {
                Panko.getUI().writeText("@GREEN Green is victorious!");
                gameWon = true;
                needsPause = true;
            }
            if (!p5lost && p1lost && p2lost && p3lost && p4lost && p6lost) {
                Panko.getUI().writeText("@GREEN Blue is victorious!");
                gameWon = true;
                needsPause = true;
            }
            if (!p6lost && p1lost && p2lost && p3lost && p4lost && p6lost) {
                Panko.getUI().writeText("@GREEN Yellow is victorious!");
                gameWon = true;
                needsPause = true;
            }
        }

        if (needsPause) {
            Panko.pauseGame();
        }


    }

    @Override
    public void start() {
        super.start();
//        int mapNum = Panko.random.nextInt(3);
        String mapName = "map2";
//        if (mapNum==1) mapName = "map2";
//        if (mapNum==2) mapName = "map3";
        PankoTmxHelper.addEntitiesToRoomFromMap(mapName, this);
        selection = (Selection)firstInstanceOfClass(Selection.class);

        Panko.getUI().setHelpText(PankoResource.loadText("help.txt"));
        Panko.getUI().setShowingHelp(true);
        Panko.pauseGame();
    }

    public void showSelectionAt(int x, int y) {
        selection.setX(x);
        selection.setY(y);
    }
}