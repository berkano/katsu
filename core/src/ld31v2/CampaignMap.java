package ld31v2;

import ext.pathfinding.grid.GridMap;
import ld31v2.entities.Selection;
import ld31v2.entities.SoldierP1;
import ld31v2.entities.SoldierP2;
import ld31v2.entities.SoldierP3;
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

        pathMap = new GridMap(32, 24);

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

        for (PankoEntity e : getEntities()) {
            if (e instanceof SoldierP1) mob1count++;
            if (e instanceof SoldierP2) mob2count++;
            if (e instanceof SoldierP3) mob3count++;
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


        if (!gameWon) {
            if (!p1lost && p2lost && p3lost) {
                Panko.getUI().writeText("@GREEN YOU ARE VICTORIOUS!");
                gameWon = true;
                needsPause = true;
            }
            if (!p2lost && p1lost && p3lost) {
                Panko.getUI().writeText("@GREEN Red is victorious!");
                gameWon = true;
                needsPause = true;
            }
            if (!p3lost && p1lost && p2lost) {
                Panko.getUI().writeText("@GREEN Purple is victorious!");
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
        PankoTmxHelper.addEntitiesToRoomFromMap("map", this);
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