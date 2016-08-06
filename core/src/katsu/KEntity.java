package katsu;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsi.Rectangle;

import java.util.List;

/**
 * Created by shaun on 15/11/2014.
 */
public class KEntity extends KInputProcessor {

    // Spatial
    @Getter private int x;
    @Getter private int y;
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

    public boolean tryMove(KEntity entity, int newX, int newY) {

        if (K.runner.gamePaused()) {
            return false;
        }

        long millisMovedAgo = K.utils.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) {
            return false;
        }

        boolean collisionDetected = false;

        if (entity.isSolid()) {

            Rectangle newRect = new Rectangle(newX, newY, newX + entity.getWidth() - 1, newY - entity.getHeight() + 1);
            List<KEntity> overlappingEntities = entity.getRoom().getSpatialMap().searchByIntersection(newRect);

            // Get all possible collision targets
            for (KEntity other : overlappingEntities) {
                if (other.isSolid() && other.canCollideWith(entity.getClass()) && entity.canCollideWith(other.getClass())) {
                    if (other != entity) {
                        collisionDetected = true;
                        entity.onCollide(other);
                        other.onCollide(entity);
                    }
                }
            }
        }

        if (collisionDetected) {
            return false;
        } else {
            entity.setX(newX);
            entity.setY(newY);
            entity.setLastMove(K.utils.currentTime());
            entity.onMoved();
            return true;
        }
    }

    public KEntity setX(int x) {

        if (x != this.x) {
            setLastMove(System.currentTimeMillis());
        }

        this.x = x;
        this.updateSpatialMap();
        return this;

    }

    private void updateSpatialMap() {

        if (getRoom() != null) {
            getRoom().getSpatialMap().update(this);
            needsSpatialUpdate = false;
        } else {
            needsSpatialUpdate = true;
        }

    }

    public KEntity setY(int y) {
        if (y != this.y) {
            setLastMove(System.currentTimeMillis());
        }
        this.y = y;
        this.updateSpatialMap();
        return this;
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

    // Intended to be overridden for special collision behaviour.
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
        K.graphics.mainCamera.position.x = getX();
        K.graphics.mainCamera.position.y = getY();
    }

    public boolean tryMove(int dx, int dy) {
        return tryMove(KDirection.fromDelta(dx, dy));
    }

    public boolean tryMove(KDirection direction) {

        if (!lastMovedMoreThan(getMaxMoveInterval())) return false;

        K.logger.pathfinder(this, "trying to move in direction " + direction);

        boolean result = false;

        setFacing(direction);
        getAppearance().setSpriteForDirection(direction);

        if (getGrid().tryMove(direction.getDx(), direction.getDy())) {
            result = true;
        }

        return result;
    }

    public KEntity nearestEntityOf(Class clazz) {
        return getRoom().nearestEntityOf(clazz, this);
    }

    // Override these to respond to events fired by framework
    public void onCollide(KEntity other) {}
    public void onMoved() {}
    public void firstUpdate() {}

}
