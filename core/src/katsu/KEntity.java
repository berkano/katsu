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

import java.util.List;

/**
 * Created by shaun on 15/11/2014.
 */
public class KEntity implements InputProcessor {

    // Spatial
    private int _x;
    private int _y;
    private int dx = 0;
    private int dy = 0;
    private double rotation = 0;
    private int parentDistance;
    private double radius;

    // Movement
    private int velocity = 0;
    private double speedOfRotationAroundParent = 0;
    private KDirection facing;

    // Appearance
    private TextureRegion textureRegion;
    private Sprite sprite;
    private int spriteRotation = 0;
    private float spriteScale = 1.0f;
    private boolean flipSpriteOnMove = false;
    private boolean spriteFlip = false;
    private boolean rotateSpriteOnMove = true;
    private int zLayer = 0;

    // Physical
    private boolean solid;

    // Events / lifecycle
    private long lastMove = K.currentTime();
    private boolean destroyed = false;
    private boolean needsSpatialUpdate = false;
    private long minMoveWait = 0;
    private boolean updateAsRogueLike = false;
    private long lastUpdate = K.currentTime();
    private boolean doneFirstUpdate = false;

    // UI
    private boolean selected = false;

    // AI
    private KEntity targetEntity;
    private KDirection pathFinderNextDirection;

    // To organise
    private KRoom room;
    private KEntity parent;

    private int maxMoveInterval = 0;

    // Stats
    private int health = 100;
    private int maxHealth = 100;

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getSpriteRotation() {
        return this.spriteRotation;
    }

    public void setSpriteRotation(int spriteRotation) {
        this.spriteRotation = spriteRotation;
    }

    public void setTarget(KEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public KEntity getTarget() {
        return this.targetEntity;
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
        this.setRotation(this.getRotation() + dr);
    }

    public void accelerate(int dv) {
        this.velocity += dv;
    }

    public void render() {
        if (textureRegion == null) {
            textureRegion = K.getUI().getTextureCache().get(this.getClass());
        }

        float xScale = spriteScale;
        float yScale = spriteScale;
        if (isSpriteFlip()) {
            xScale = -xScale;
        }
        K.getUI().getActiveSpriteBatch().draw(
                textureRegion,
                getX(), getY(), getWidth() / 2, getHeight() / 2,
                getWidth(), getHeight(),
                xScale, yScale, (float) spriteRotation
        );

    }

    public boolean moveGrid(int dx, int dy) {

        int newX = getX() + dx * K.getGridSize();
        int newY = getY() + dy * K.getGridSize();

        return moveEntityIfPossible(this, newX, newY);

    }

    private boolean moveEntityIfPossible(KEntity entity, int newX, int newY) {

        if (K.gamePaused()) {
            KLog.pathfinder(this, "game paused");
            return false;
        }

        long millisMovedAgo = K.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) {
            KLog.pathfinder(this, "last move was too recent");
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
                        KLog.pathfinder(this, "Would collide with entity " + other.getClass().getSimpleName());
                        entity.onCollide(other);
                        other.onCollide(entity);
                    }
                }
            }
        }

        if (couldMove) {
            KLog.pathfinder(this, "passed movement rules! going from " + getX() + "," + getY() + " to " + newX + "," + newY);
            entity.setX(newX);
            entity.setY(newY);
            entity.setLastMove(K.currentTime());
            entity.onMoved();
        }

        return couldMove;

    }

    public void juiceMySprite(float juicyness) {

        this.setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(15) - 7);
        this.setSpriteScale(K.random.nextFloat() * juicyness + 1.0f + juicyness);


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
    
    public TextureRegion getTextureRegion() {
        return textureRegion;
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

    
    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }
    
    public void onCollide(KEntity other) {

    }

    public int getWidth() {
        return K.getSettings().getGridSize();
    }

    
    public void onMoved() {

    }

    
    public int getHeight() {
        return K.getSettings().getGridSize();
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
        return K.currentTime() > getLastMove() + timeLimit;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public KEntity setParentBody(KEntity parent, int distance, double speedOfRotationAroundParent) {
        this.parent = parent;
        this.parentDistance = distance;
        this.speedOfRotationAroundParent = speedOfRotationAroundParent;
        return this;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public int getVelocity() {
        return this.velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public boolean doesContain(Vector3 point) {
        Rectangle rect = new Rectangle(getX(), getY(), getWidth(), getHeight());
        return rect.contains(point.x, point.y);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


    public float getSpriteScale() {
        return spriteScale;
    }

    public void setSpriteScale(float spriteScale) {
        this.spriteScale = spriteScale;
    }

    public int getMaxMoveInterval() {
        return maxMoveInterval;
    }

    public void setMaxMoveInterval(int maxMoveInterval) {
        this.maxMoveInterval = maxMoveInterval;
    }

    
    public int getHealth() {
        return health;
    }

    
    public void setHealth(int health) {
        this.health = health;
    }

    
    public int getMaxHealth() {
        return maxHealth;
    }

    
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public KDirection getPathFinderNextDirection() {
        return pathFinderNextDirection;
    }

    public void setPathFinderNextDirection(KDirection pathFinderNextDirection) {
        this.pathFinderNextDirection = pathFinderNextDirection;
    }

    
    public int getzLayer() {
        return zLayer;
    }

    public void setzLayer(int zLayer) {
        this.zLayer = zLayer;
    }

    public void lookAtMe() {
        K.getUI().getMainCamera().position.x = getX();
        K.getUI().getMainCamera().position.y = getY();
    }

    public boolean moveRequested(int dx, int dy) {
        return moveRequested(KDirection.fromDelta(dx, dy));
    }

    public boolean moveRequested(KDirection direction) {

        if (!lastMovedMoreThan(getMaxMoveInterval())) return false;

        KLog.pathfinder(this, "trying to move in direction " + direction);

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

    public void setFacing(KDirection facing) {
        this.facing = facing;
    }

    public KDirection getFacing() {
        return facing;
    }

    public void setRotateSpriteOnMove(boolean rotateSpriteOnMove) {
        this.rotateSpriteOnMove = rotateSpriteOnMove;
    }

    public boolean isRotateSpriteOnMove() {
        return rotateSpriteOnMove;
    }

    public long getMinMoveWait() {
        return minMoveWait;
    }

    public void setMinMoveWait(long minMoveWait) {
        this.minMoveWait = minMoveWait;
    }

    
    public void setUpdateAsRogueLike(boolean updateAsRogueLike) {
        this.updateAsRogueLike = updateAsRogueLike;
    }

    
    public boolean isUpdateAsRogueLike() {
        return updateAsRogueLike;
    }

    
    public long getLastUpdate() {
        return lastUpdate;
    }

    
    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setFlipSpriteOnMove(boolean flipSpriteOnMove) {
        this.flipSpriteOnMove = flipSpriteOnMove;
    }

    public boolean isFlipSpriteOnMove() {
        return flipSpriteOnMove;
    }

    public void setSpriteFlip(boolean spriteFlip) {
        this.spriteFlip = spriteFlip;
    }

    public boolean isSpriteFlip() {
        return spriteFlip;
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
        setX(gridX * K.getGridSize());
    }

    public void setGridY(int gridY) {
        setY(gridY * K.getGridSize());
    }

    public boolean gridIsEmpty(int gridX, int gridY) {

        List<KEntity> entities = getRoom().findEntitiesAtPoint(gridX * K.getGridSize(), gridY * K.getGridSize());
        for (KEntity e : entities) {
            if (e.getGridX() == gridX) {
                if (e.getGridY() == gridY) {

                    if (e.isSolid()) {
                        KLog.trace("grid is not empty due to " + e.getClass().getSimpleName());

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
        List<KEntity> entities = getRoom().findEntitiesAtPoint(gridX * K.getGridSize(), gridY * K.getGridSize());
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

        KLog.pathfinder(this, "get path from " + startX + "," + startY + " to " + endX + "," + endY);

        GridLocation start = new GridLocation(startX, startY, false);
        GridLocation end = new GridLocation(endX, endY, true);
        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);

        if (gridPath != null) {
            if (gridPath.getList().size() > 1) {
                GridLocation nextMove = gridPath.getList().get(gridPath.getList().size() - 2); // last entry?
                if (nextMove != null) {
                    suggestedDx = nextMove.getX() - getGridX();
                    suggestedDy = nextMove.getY() - getGridY();
                    KLog.pathfinder(this, "pathfinder suggests delta of " + suggestedDx + "," + suggestedDy);
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
        setTextureRegion(K.getUI().getTextureCache().get(clazz));
    }


}
