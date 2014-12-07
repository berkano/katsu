package ld31v2;

import ext.pathfinding.grid.GridMap;
import ld31v2.entities.Selection;
import ld31v2.entities.SoldierP1;
import ld31v2.entities.SoldierP2;
import ld31v2.entities.SoldierP3;
import panko.Panko;
import panko.PankoEntity;
import panko.PankoRoomBase;
import panko.PankoTmxHelper;

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

        if (mob1count == 0) {
            if (!p1lost) {
                p1lost = true;
                Panko.getUI().writeText("@RED Blue lost the game!");
            }
        }

        if (mob2count == 0) {
            if (!p2lost) {
                p2lost = true;
                Panko.getUI().writeText("@RED Red lost the game!");
            }
        }

        if (mob3count == 0) {
            if (!p3lost) {
                p3lost = true;
                Panko.getUI().writeText("@RED Purple lost the game!");
            }
        }


        if (!gameWon) {
            if (!p1lost && p2lost && p3lost) {
                Panko.getUI().writeText("@GREEN Blue wins the game!");
                gameWon = true;
            }
            if (!p2lost && p1lost && p3lost) {
                Panko.getUI().writeText("@GREEN Red wins the game!");
                gameWon = true;
            }
            if (!p3lost && p1lost && p3lost) {
                Panko.getUI().writeText("@GREEN Purple wins the game!");
                gameWon = true;
            }
        }


    }

    @Override
    public void start() {
        super.start();
        PankoTmxHelper.addEntitiesToRoomFromMap("map", this);
        selection = (Selection)firstInstanceOfClass(Selection.class);
    }

    public void showSelectionAt(int x, int y) {
        selection.setX(x);
        selection.setY(y);
    }
}