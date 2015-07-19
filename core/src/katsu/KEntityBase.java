package katsu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by shaun on 15/11/2014.
 */
public abstract class KEntityBase implements KEntity, InputProcessor {

    private int x;
    private int y;
    private int dx = 0;
    private int dy = 0;
    private double rotation = 0;
    private int velocity = 0;
    private TextureRegion textureRegion;
    private Sprite sprite;
    private boolean solid;
    private KRoom room;
    private long lastMove = K.currentTime();
    private KEntity parent;
    private int parentDistance;
    private double speedOfRotationAroundParent = 0;
    private double radius;
    private boolean selected = false;
    private KEntity targetEntity;
    private boolean beingRemoved = false;
    private int spriteRotation = 0;
    private float spriteScale = 1.0f;
    private int maxMoveInterval = 0;
    private int health = 100;
    private int maxHealth = 100;
    private KDirection pathFinderNextDirection;

    public int getSpriteRotation(){
        return this.spriteRotation;
    }

    public void setSpriteRotation(int spriteRotation) {
        this.spriteRotation = spriteRotation;
    }

    @Override
    public void setTarget(KEntity targetEntity) {
        this.targetEntity = targetEntity;
    }

    @Override
    public KEntity getTarget() {
        return this.targetEntity;
    }

    @Override
    public boolean overlaps(KEntity other) {
        if (other.getX() <= getX() - other.getWidth()) return false;
        if (other.getY() <= getY() - other.getHeight()) return false;
        if (other.getX() >= getX() + getWidth()) return false;
        if (other.getY() >= getY() + getHeight()) return false;
        return true;
    }

    @Override
    public int getGridY() {
        return getY() / getHeight();
    }

    @Override
    public boolean isBeingRemoved() {
        return this.beingRemoved;
    }

    @Override
    public void setBeingRemoved(boolean beingRemoved) {
        this.beingRemoved = beingRemoved;
    }

    @Override
    public int getGridX() {
        return getX() / getWidth();
    }

    @Override
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

    @Override
    public void render() {
        if (textureRegion == null) {
            textureRegion = KGraphics.getTextureCache().get(this.getClass());
        }
        //Panko.getActiveSpriteBatch().draw(textureRegion, x, y);
        K.getActiveSpriteBatch().draw(
                textureRegion,
                x, y, getWidth()/2,getHeight()/2,
                getWidth(), getHeight(),
                spriteScale, spriteScale, (float)spriteRotation
        );

//    public void draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height,
  //                    float scaleX, float scaleY, float rotation) {

    }

    @Override
    public boolean moveGrid(int dx, int dy) {
        int newX = getX() + dx * K.getGridSize();
        int newY = getY() + dy * K.getGridSize();

        return KMovementConstrainer.moveEntityIfPossible(this, newX, newY);

    }

    @Override
    public KEntity setX(int x) {
        this.x = x;
        return this;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public KEntity setY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public KEntity setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        return this;
    }

    @Override
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }

    @Override
    public void onCollide(KEntity other) {

    }

    @Override
    public int getWidth() {
        return K.getSettings().getGridSize();
    }

    @Override
    public void onMoved() {

    }

    @Override
    public int getHeight() {
        return K.getSettings().getGridSize();
    }

    @Override
    public KEntity setLastMove(long time) {
        this.lastMove = time;
        return this;
    }

    @Override
    public void createInPlace(Class clazz) {
        try {
            KEntity newEntity = (KEntity)clazz.newInstance();
            newEntity.setX(getX());
            newEntity.setY(getY());
            getRoom().getEntities().add(newEntity);
        } catch (Exception ex) {

        }

    }

    @Override
    public void update() {
        x -= velocity * Math.sin(getRotation() * 0.0174);
        y += velocity * Math.cos(getRotation() * 0.0174);

        if (this.parent != null) {
            double rx = -1 * (double)parentDistance * Math.sin(getRotation() * 0.0174);
            double ry = (double)parentDistance * Math.cos(getRotation() * 0.0174);
            setX(parent.getX() + (int)Math.round(rx));
            setY(parent.getY() + (int) Math.round(ry));
        }

        if (this.speedOfRotationAroundParent != 0) {
            rotation += this.speedOfRotationAroundParent;
        }

        if (getHealth() <= 0) {
            setBeingRemoved(true);
            getRoom().getDeadEntities().add(this);
        }
    }

    @Override
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

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public KDirection getPathFinderNextDirection() {
        return pathFinderNextDirection;
    }

    public void setPathFinderNextDirection(KDirection pathFinderNextDirection) {
        this.pathFinderNextDirection = pathFinderNextDirection;
    }
}
