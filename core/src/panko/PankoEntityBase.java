package panko;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 15/11/2014.
 */
public abstract class PankoEntityBase implements PankoEntity, InputProcessor {

    private int x;
    private int y;
    private TextureRegion textureRegion;

    @Override
    public void render() {
        Panko.getActiveSpriteBatch().draw(textureRegion, x, y);
    }

    @Override
    public boolean moveGrid(int dx, int dy) {
        setX(getX() + dx * Panko.getGridSize());
        setY(getY()+ dy * Panko.getGridSize());
        return true; // TODO collision detection here - return if it was successful or not
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
}
