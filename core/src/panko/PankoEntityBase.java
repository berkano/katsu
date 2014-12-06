package panko;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 15/11/2014.
 */
public abstract class PankoEntityBase implements PankoEntity, InputProcessor {

    private int x;
    private int y;
    private int dx = 0;
    private int dy = 0;
    private int rotation = 0;
    private int velocity = 0;
    private TextureRegion textureRegion;
    private Sprite sprite;
    private boolean solid;
    private PankoRoom room;
    private long lastMove = Panko.currentTime();

    public void rotate(int dr) {
        this.setRotation(this.getRotation() + dr);
    }

    public void accelerate(int dv) {
        this.velocity += dv;
    }

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
        x -= velocity * Math.sin(getRotation() * 0.0174);
        y += velocity * Math.cos(getRotation() * 0.0174);
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

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
