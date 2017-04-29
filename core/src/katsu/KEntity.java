package katsu;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsi.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    @Getter @Setter private long lastMove = System.currentTimeMillis();
    @Getter @Setter private boolean destroyed = false;
    @Getter @Setter private boolean needsSpatialUpdate = false;
    @Getter @Setter private long minMoveWait = 0;
    @Getter @Setter private boolean updateAsRogueLike = false;
    @Getter @Setter private long lastUpdate = System.currentTimeMillis();
    @Getter @Setter private boolean doneFirstUpdate = false;

    // TrollCastleUI
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

    @Getter @Setter private long createTimeMillis = System.currentTimeMillis();

    private Logger logger = LoggerFactory.getLogger(KEntity.class);

    public long ageMillis() {
        return System.currentTimeMillis() - createTimeMillis;
    }

    void render() {
        appearance.render();
    }

    boolean tryMoveAbsolutePoint(int newX, int newY) {

        if (K.game.gamePaused()) {
            return false;
        }

        long millisMovedAgo = System.currentTimeMillis() - getLastMove();
        if (millisMovedAgo < getMaxMoveInterval()) {
            return false;
        }

        boolean collisionDetected = false;

        if (isSolid()) {

            Rectangle newRect = new Rectangle(newX, newY, newX + getWidth() - 1, newY - getHeight() + 1);
            List<KEntity> overlappingEntities = getRoom().getSpatialMap().searchByIntersection(newRect);

            // Get all possible collision targets
            for (KEntity other : overlappingEntities) {
                if (other.isSolid() && other.canCollideWith(getClass()) && canCollideWith(other.getClass())) {
                    if (other != this) {
                        collisionDetected = true;
                        onCollide(other);
                        other.onCollide(this);
                    }
                }
            }
        }

        if (collisionDetected) {
            return false;
        } else {
            setX(newX);
            setY(newY);
            setLastMove(System.currentTimeMillis());
            onMoved();
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
    private boolean canCollideWith(Class clazz) {
        return true;
    }

    public void destroy() {
        setDestroyed(true);
    }

    protected boolean lastMovedMoreThan(int timeLimit) {
        return System.currentTimeMillis() > getLastMove() + timeLimit;
    }

    public void lookAtMe() {

        K.graphics.camera.position.x = getX() + getHeight() / 2;
        K.graphics.camera.position.y = getY() + getHeight() / 2;

    }

    public boolean tryMoveGridRelative(int dx, int dy) {
        return tryMoveGridDirection(KDirection.fromDelta(dx, dy));
    }

    protected boolean tryMoveGridDirection(KDirection direction) {

        if (!lastMovedMoreThan(getMaxMoveInterval())) return false;

        logger.debug("pathfinder: trying to move in direction " + direction);

        boolean result = false;

        setFacing(direction);
        getAppearance().setSpriteForDirection(direction);

        if (getGrid().tryMoveRelativeCell(direction.getDx(), direction.getDy())) {
            result = true;
        }

        return result;
    }

    public KEntity nearestEntityOf(Class clazz) {
        return getRoom().nearestEntityOf(clazz, this);
    }

    // Override these to respond to events fired by framework
    private void onCollide(KEntity other) {}
    private void onMoved() {}
    private void firstUpdate() {}

    public void addHealth(int delta) {
        this.setHealth(getHealth() + delta);
    }

    public KEntity getInstanceUnderneath(Class clazz) {
        ArrayList<KEntity> elist = room.findEntitiesAtPoint(x + 2, y + 2);
        for (KEntity e : elist) {
            if (clazz.isInstance(e)) return e;
        }
        return null;
    }

    public void capHealth(int maxHealth) {
        if (health > maxHealth) health = maxHealth;
    }

    public boolean isOnTopOf(Class clazz) {
        return getInstanceUnderneath(clazz) != null;
    }

    public void setPosition(KEntity other) {
        setX(other.getX());
        setY(other.getY());
    }

    public void onClick() {

    }
}
