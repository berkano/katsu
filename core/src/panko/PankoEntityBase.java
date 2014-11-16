package panko;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 15/11/2014.
 */
public abstract class PankoEntityBase implements PankoEntity {

    private int x;
    private int y;
    private TextureRegion textureRegion;

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
}
