package katsu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by shaun on 15/11/2014.
 */
public class KEntity extends KEntityBase {

    // Spatial
    private int _x;
    private int _y;
    @Getter @Setter private int dx = 0;
    @Getter @Setter private int dy = 0;

    // Movement
    @Getter @Setter private KDirection facing;
    @Getter @Setter private KGrid grid = new KGrid(this);

    // Appearance
    @Getter @Setter private KAppearance appearance = new KAppearance(this);

    // Physical
    @Getter @Setter private boolean solid;

    // Events / lifecycle
    @Getter @Setter private long lastMove = K.utils.currentTime();
    @Getter @Setter private boolean destroyed = false;
    @Getter @Setter private boolean needsSpatialUpdate = false;
    @Getter @Setter private long minMoveWait = 0;
    @Getter @Setter private boolean updateAsRogueLike = false;
    @Getter @Setter private long lastUpdate = K.utils.currentTime();
    @Getter @Setter private boolean doneFirstUpdate = false;

    // UI
    @Getter @Setter private boolean selected = false;

    // AI
    @Getter @Setter private KEntity targetEntity;
    @Getter @Setter private KDirection pathFinderNextDirection;

    // To organise
    @Getter @Setter private KRoom room;

    @Getter @Setter private int maxMoveInterval = 0;

    // Stats
    @Getter @Setter private int health = 100;
    @Getter @Setter private int maxHealth = 100;

    public void render() {
        appearance.render();
    }

    public boolean moveEntityIfPossible(KEntity entity, int newX, int newY) {

        if (K.runner.gamePaused()) {
            return false;
        }

        long millisMovedAgo = K.utils.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) {
            return false;
        }

        boolean couldMove = true;

        if (entity.isSolid()) {

            net.sf.jsi.Rectangle newRect = new net.sf.jsi.Rectangle(newX, newY, newX + entity.getWidth() - 1, newY - entity.getHeight() + 1);
            List<KEntity> overlappingEntities = entity.getRoom().spatialSearchByIntersection(newRect);

            // Get all possible collision targets
            for (KEntity other : overlappingEntities) {
                if (other.isSolid() && other.canCollideWith(entity.getClass()) && entity.canCollideWith(other.getClass())) {
                    if (other != entity) {
                        couldMove = false;
                        entity.onCollide(other);
                        other.onCollide(entity);
                    }
                }
            }
        }

        if (couldMove) {
            entity.setX(newX);
            entity.setY(newY);
            entity.setLastMove(K.utils.currentTime());
            entity.onMoved();
        }

        return couldMove;

    }

    public KEntity setX(int x) {

        if (x != _x) {
            setLastMove(System.currentTimeMillis());
        }

        this._x = x;
        this.updateSpatialMap();
        return this;

    }

    private void updateSpatialMap() {

        if (getRoom() != null) {
            getRoom().updateSpatialMap(this);
            needsSpatialUpdate = false;
        } else {
            needsSpatialUpdate = true;
        }

    }

    public int getX() {
        return _x;
    }

    public KEntity setY(int y) {
        if (y != _y) {
            setLastMove(System.currentTimeMillis());
        }
        this._y = y;
        this.updateSpatialMap();
        return this;
    }
    
    public int getY() {
        return _y;
    }

    public int getWidth() {
        return K.settings.getGridSize();
    }

    public int getHeight() {
        return K.settings.getGridSize();
    }

    public void update() {

        if (!doneFirstUpdate) {
            firstUpdate();
            doneFirstUpdate = true;
        }

        lastUpdate = System.currentTimeMillis();

        if (needsSpatialUpdate) {
            updateSpatialMap();
        }

        if (getHealth() <= 0) {
            destroy();
        }
    }

    public boolean canCollideWith(Class clazz) {
        return true;
    }

    public void destroy() {
        setDestroyed(true);
    }

    public boolean lastMovedMoreThan(int timeLimit) {
        return K.utils.currentTime() > getLastMove() + timeLimit;
    }

    public void lookAtMe() {
        K.ui.getMainCamera().position.x = getX();
        K.ui.getMainCamera().position.y = getY();
    }

    public boolean moveRequested(int dx, int dy) {
        return moveRequested(KDirection.fromDelta(dx, dy));
    }

    public boolean moveRequested(KDirection direction) {

        if (!lastMovedMoreThan(getMaxMoveInterval())) return false;

        K.logger.pathfinder(this, "trying to move in direction " + direction);

        boolean result = false;

        setFacing(direction);
        getAppearance().setSpriteForDirection(direction);

        if (getGrid().move(direction.getDx(), direction.getDy())) {
            result = true;
        }

        return result;
    }

    public KEntity nearestEntityOf(Class clazz) {

        KEntity result = null;

        long nearestDistance = 99999999;

        List<KEntity> entities = getRoom().getEntities();
        for (KEntity e : entities) {
            if (clazz.isInstance(e)) {

                int dx = Math.abs(getX() - e.getX());
                int dy = Math.abs(getY() - e.getY());
                long dist = Math.round(Math.sqrt(dx * dx + dy * dy));
                if (dist < nearestDistance) {
                    nearestDistance = dist;
                    result = e;
                }
            }
        }

        return result;

    }

}
