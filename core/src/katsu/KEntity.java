package katsu;

import com.badlogic.gdx.InputProcessor;
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
public class KEntity implements InputProcessor {

    // Spatial
    private int _x;
    private int _y;
    @Getter @Setter private int dx = 0;
    @Getter @Setter private int dy = 0;
    @Getter @Setter private double rotation = 0;
    @Getter @Setter private int parentDistance;
    @Getter @Setter private double radius;

    // Movement
    @Getter @Setter private int velocity = 0;
    @Getter @Setter private double speedOfRotationAroundParent = 0;
    @Getter @Setter private KDirection facing;

    // Appearance
    private TextureRegion textureRegion;
    @Getter @Setter private Sprite sprite;
    @Getter @Setter private int spriteRotation = 0;
    @Getter @Setter private float spriteScale = 1.0f;
    @Getter @Setter private boolean flipSpriteOnMove = false;
    @Getter @Setter private boolean spriteFlip = false;
    @Getter @Setter private boolean rotateSpriteOnMove = true;
    @Getter @Setter private int zLayer = 0;

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
    @Getter @Setter private KEntity parent;

    @Getter @Setter private int maxMoveInterval = 0;

    // Stats
    @Getter @Setter private int health = 100;
    @Getter @Setter private int maxHealth = 100;

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean overlaps(KEntity other) {
        if (other.getX() <= getX() - other.getWidth()) return false;
        if (other.getY() <= getY() - other.getHeight()) return false;
        if (other.getX() >= getX() + getWidth()) return false;
        if (other.getY() >= getY() + getHeight()) return false;
        return true;
    }

    public int getGridY() {
        return getY() / getHeight();
    }

    public int getGridX() {
        return getX() / getWidth();
    }

    public boolean wouldOverlap(KEntity other, int nx, int ny) {
        if (nx <= getX() - other.getWidth()) return false;
        if (ny <= getY() - other.getHeight()) return false;
        if (nx >= getX() + getWidth()) return false;
        if (ny >= getY() + getHeight()) return false;
        return true;
    }

    public void rotate(double dr) {
        setRotation(getRotation() + dr);
    }

    public void accelerate(int dv) {
        this.velocity += dv;
    }

    public void render() {
        if (textureRegion == null) {
            textureRegion = K.ui.getTextureCache().get(this.getClass());
        }

        float xScale = spriteScale;
        float yScale = spriteScale;
        if (isSpriteFlip()) {
            xScale = -xScale;
        }
        K.ui.getActiveSpriteBatch().draw(
                textureRegion,
                getX(), getY(), getWidth() / 2, getHeight() / 2,
                getWidth(), getHeight(),
                xScale, yScale, (float) spriteRotation
        );

    }

    public boolean moveGrid(int dx, int dy) {

        int newX = getX() + dx * K.settings.getGridSize();
        int newY = getY() + dy * K.settings.getGridSize();

        return moveEntityIfPossible(this, newX, newY);

    }

    private boolean moveEntityIfPossible(KEntity entity, int newX, int newY) {

        if (K.gamePaused()) {
            K.logger.pathfinder(this, "game paused");
            return false;
        }

        long millisMovedAgo = K.utils.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) {
            K.logger.pathfinder(this, "last move was too recent");
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
                        K.logger.pathfinder(this, "Would collide with entity " + other.getClass().getSimpleName());
                        entity.onCollide(other);
                        other.onCollide(entity);
                    }
                }
            }
        }

        if (couldMove) {
            K.logger.pathfinder(this, "passed movement rules! going from " + getX() + "," + getY() + " to " + newX + "," + newY);
            entity.setX(newX);
            entity.setY(newY);
            entity.setLastMove(K.utils.currentTime());
            entity.onMoved();
        }

        return couldMove;

    }

    public void juiceMySprite(float juiceiness) {

        this.setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(15) - 7);
        this.setSpriteScale(K.random.nextFloat() * juiceiness + 1.0f + juiceiness);


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

    
    public KEntity setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }
    
    public boolean keyDown(int keycode) {
        return false;
    }

    
    public boolean keyUp(int keycode) {
        return false;
    }

    
    public boolean keyTyped(char character) {
        return false;
    }

    
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    
    public boolean scrolled(int amount) {
        return false;
    }

    
    public void onCollide(KEntity other) {

    }

    public int getWidth() {
        return K.settings.getGridSize();
    }

    public void onMoved() {

    }

    
    public int getHeight() {
        return K.settings.getGridSize();
    }

    
    public KEntity setLastMove(long time) {
        this.lastMove = time;
        return this;
    }

    
    public void createInPlace(Class clazz) {
        try {
            KEntity newEntity = (KEntity) clazz.newInstance();
            newEntity.setX(getX());
            newEntity.setY(getY());
            getRoom().getEntities().add(newEntity);
        } catch (Exception ex) {

        }

    }

    
    public void addNewEntity(KEntity entity) {
        getRoom().addNewEntity(entity);
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

        if (this.parent != null) {
            double rx = -1 * (double) parentDistance * Math.sin(getRotation() * 0.0174);
            double ry = (double) parentDistance * Math.cos(getRotation() * 0.0174);
            setX(parent.getX() + (int) Math.round(rx));
            setY(parent.getY() + (int) Math.round(ry));
        }

        if (this.speedOfRotationAroundParent != 0) {
            rotation += this.speedOfRotationAroundParent;
        }

        if (getHealth() <= 0) {
            destroy();
        }
    }

    
    public boolean canCollideWith(Class clazz) {
        return true;
    }

    public KRoom getRoom() {
        return room;
    }

    public KEntity setRoom(KRoom room) {
        this.room = room;
        return this;
    }

    
    public void destroy() {
        setDestroyed(true);
    }

    public long getLastMove() {
        return lastMove;
    }

    public boolean lastMovedMoreThan(int timeLimit) {
        return K.utils.currentTime() > getLastMove() + timeLimit;
    }

    public KEntity setParentBody(KEntity parent, int distance, double speedOfRotationAroundParent) {
        this.parent = parent;
        this.parentDistance = distance;
        this.speedOfRotationAroundParent = speedOfRotationAroundParent;
        return this;
    }

    public boolean doesContain(Vector3 point) {
        Rectangle rect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        return rect.contains(point.x, point.y);
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

        if (isRotateSpriteOnMove()) {
            setSpriteRotation(direction.rotation());
        }
        if (isFlipSpriteOnMove()) {
            if (direction.equals(KDirection.LEFT)) {
                setSpriteFlip(true);
            }
            if (direction.equals(KDirection.RIGHT)) {
                setSpriteFlip(false);
            }
        }

        if (moveGrid(direction.dx(), direction.dy())) {
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

    public void setGridX(int gridX) {
        setX(gridX * K.settings.getGridSize());
    }

    public void setGridY(int gridY) {
        setY(gridY * K.settings.getGridSize());
    }

    public boolean gridIsEmpty(int gridX, int gridY) {

        List<KEntity> entities = getRoom().findEntitiesAtPoint(gridX * K.settings.getGridSize(), gridY * K.settings.getGridSize());
        for (KEntity e : entities) {
            if (e.getGridX() == gridX) {
                if (e.getGridY() == gridY) {

                    if (e.isSolid()) {
                        K.logger.trace("grid is not empty due to " + e.getClass().getSimpleName());

                        return false;
                    }
                }
            }
        }
        return true;
    }

    public KEntity findFirstEntityOnGrid(Class clazz, int gridX, int gridY) {
        if (clazz == null) {
            throw new RuntimeException("Null class provided to findFirstEntityOnGrid");
        }
        List<KEntity> entities = getRoom().findEntitiesAtPoint(gridX * K.settings.getGridSize(), gridY * K.settings.getGridSize());
        for (KEntity e : entities) {
            if (e.getGridX() == gridX) {
                if (e.getGridY() == gridY) {
                    if (clazz.isInstance(e)) return e;
                }
            }
        }
        return null;
    }

    public KDirection doPathFinding(int targetGridX, int targetGridY) {

        GridMap pathMap = createPathMap(targetGridX, targetGridY);

        GridPathfinding gridPathfinding = new GridPathfinding();

        int startX = getGridX();
        int startY = getGridY();
        int endX = targetGridX;
        int endY = targetGridY;

        int suggestedDx = 0;
        int suggestedDy = 0;

        K.logger.pathfinder(this, "get path from " + startX + "," + startY + " to " + endX + "," + endY);

        GridLocation start = new GridLocation(startX, startY, false);
        GridLocation end = new GridLocation(endX, endY, true);
        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);

        if (gridPath != null) {
            if (gridPath.getList().size() > 1) {
                GridLocation nextMove = gridPath.getList().get(gridPath.getList().size() - 2); // last entry?
                if (nextMove != null) {
                    suggestedDx = nextMove.getX() - getGridX();
                    suggestedDy = nextMove.getY() - getGridY();
                    K.logger.pathfinder(this, "pathfinder suggests delta of " + suggestedDx + "," + suggestedDy);
                }
            }

        }

        KDirection suggested = KDirection.fromDelta(suggestedDx, suggestedDy);
        return suggested;

    }

    private GridMap createPathMap(int targetGridX, int targetGridY) {

        GridMap pathMap = new GridMap(getRoom().getGridWidth(), getRoom().getGridHeight());

        for (KEntity e : getRoom().getEntities()) {
            if (e == this) continue;
            if (!(e.isSolid())) continue;

            int cellX = e.getGridX();
            int cellY = e.getGridY();

            if (cellX == targetGridX && cellY == targetGridY) continue; // allow attempted path finds up to the target

            if (cellX >= 0 && cellY >= 0 && cellX <= 100 && cellY <= 100) {
                pathMap.set(cellX, cellY, GridMap.WALL);
            }
        }

        return pathMap;

    }

    public void firstUpdate() {

    }

    public void setTextureFrom(Class clazz) {
        setTextureRegion(K.ui.getTextureCache().get(clazz));
    }


}
