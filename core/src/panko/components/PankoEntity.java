package panko.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 15/11/2014.
 */
public class PankoEntity {

    private int x;
    private int y;
    private TextureRegion textureRegion;

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
