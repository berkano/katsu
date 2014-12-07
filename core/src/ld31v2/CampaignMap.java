package ld31v2;

import ext.pathfinding.grid.GridMap;
import ld31v2.entities.Selection;
import panko.PankoEntity;
import panko.PankoRoomBase;
import panko.PankoTmxHelper;

/**
 * Created by shaun on 06/12/2014.
 */
public class CampaignMap extends PankoRoomBase {

    private Selection selection;
    private static GridMap pathMap;

    public static GridMap getPathMap() {
        return pathMap;
    }

    @Override
    public void update() {
        super.update();
        updatePathMap();
    }

    private void updatePathMap() {

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