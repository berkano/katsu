package panko;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by shaun on 15/11/2014.
 */
public abstract class PankoEntityBase implements PankoEntity, InputProcessor {

    private int x;
    private int y;
    private TextureRegion textureRegion;
    private Sprite sprite;
    private boolean solid;
    private PankoRoom room;
    private long lastMove = Panko.currentTime();
    private Body body;
    private World world;
    private boolean physics;

    @Override
    public void render() {
        Panko.getActiveSpriteBatch().draw(textureRegion, x, y);
    }

    @Override
    public boolean moveGrid(int dx, int dy) {
        int newX = getX() + dx * Panko.getGridSize();
        int newY = getY() + dy * Panko.getGridSize();

        return PankoCollisionDetector.moveEntityIfPossible(this, newX, newY);

    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
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
    public void onCollide(PankoEntity other) {

    }

    @Override
    public int getWidth() {
        return Panko.getSettings().getGridSize();
    }

    @Override
    public int getHeight() {
        return Panko.getSettings().getGridSize();
    }

    @Override
    public void setLastMove(long time) {
        this.lastMove = time;
    }

    @Override
    public void update() {

    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public World getWorld() {
        return world;
    }

    public PankoRoom getRoom() {
        return room;
    }

    public void setRoom(PankoRoom room) {
        this.room = room;
    }

    public long getLastMove() {
        return lastMove;
    }

    public boolean lastMovedMoreThan(int timeLimit) {
        return Panko.currentTime() > getLastMove() + timeLimit;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public boolean isPhysics() {
        return physics;
    }

    public void setPhysics(boolean physics) {
        this.physics = physics;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
