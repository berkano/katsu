package katsu.spatial;

import katsu.spatial.pathfinding.grid.GridLocation;
import katsu.spatial.pathfinding.grid.GridMap;
import katsu.spatial.pathfinding.grid.GridPath;
import katsu.spatial.pathfinding.grid.GridPathfinding;
import katsu.K;
import katsu.model.KEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by shaun on 17/07/2016.
 */
public class KGrid {
    private KEntity entity;

    Logger logger = LoggerFactory.getLogger(KGrid.class);

    public KGrid(KEntity entity) {
        this.entity = entity;
    }

    public int getX() {
        return entity.getX() / K.settings.getGridSize();
    }

    public int getY() {
        return entity.getY() / K.settings.getGridSize();
    }

    public boolean tryMoveRelativeCell(int dx, int dy) {

        int newX = (getX() + dx) * K.settings.getGridSize();
        int newY = (getY() + dy) * K.settings.getGridSize();

        return entity.tryMoveAbsolutePoint(newX, newY);

    }

    public boolean isEmpty(int gridX, int gridY) {
        List<KEntity> entities = entity.getRoom().findEntitiesAtPoint(gridX * K.settings.getGridSize(), gridY * K.settings.getGridSize());
        for (KEntity e : entities) {
            if (e.getGrid().getX() == gridX && e.getGrid().getY() == gridY && e.isSolid()) {
                logger.trace("grid is not empty due to " + e.getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }

    public KDirection doPathFinding(int endX, int endY) {

        GridMap pathMap = createPathMap(endX, endY);

        GridPathfinding gridPathfinding = new GridPathfinding();

        int startX = getX();
        int startY = getY();

        int suggestedDx = 0;
        int suggestedDy = 0;

        logger.debug("pathfinder: " + entity.toString()+ ": get path from " + startX + "," + startY + " to " + endX + "," + endY);

        GridLocation start = new GridLocation(startX, startY, false);
        GridLocation end = new GridLocation(endX, endY, true);
        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);

        if (gridPath != null) {
            if (gridPath.getList().size() > 1) {
                GridLocation nextMove = gridPath.getList().get(gridPath.getList().size() - 2); // last entry?
                if (nextMove != null) {
                    suggestedDx = nextMove.getX() - getX();
                    suggestedDy = nextMove.getY() - getY();
                    logger.debug("pathfinder: " + entity.toString()+ "pathfinder suggests delta of " + suggestedDx + "," + suggestedDy);
                }
            }

        }

        return KDirection.fromDelta(suggestedDx, suggestedDy);
    }

    private GridMap createPathMap(int targetGridX, int targetGridY) {

        GridMap pathMap = new GridMap(entity.getRoom().getGridWidth(), entity.getRoom().getGridHeight());

        for (KEntity e : entity.getRoom().getEntities()) {
            if (e == entity) continue;
            if (!(e.isSolid())) continue;

            int cellX = e.getGrid().getX();
            int cellY = e.getGrid().getY();

            if (cellX == targetGridX && cellY == targetGridY) continue; // allow attempted path finds up to the target

            if (cellX >= 0 && cellY >= 0 && cellX <= 100 && cellY <= 100) {
                pathMap.set(cellX, cellY, GridMap.WALL);
            }
        }

        return pathMap;

    }

    public void setX(int gridX) {
        entity.setX(gridX * K.settings.getGridSize());
    }

    public void setY(int gridY) {
        entity.setY(gridY * K.settings.getGridSize());
    }

}
