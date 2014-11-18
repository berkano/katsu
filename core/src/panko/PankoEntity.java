package panko;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 16/11/2014.
 */
public interface PankoEntity {
    void setX(int x);

    int getX();

    void setY(int y);

    int getY();

    void setTextureRegion(TextureRegion textureRegion);

    TextureRegion getTextureRegion();

    void render();

    boolean moveGrid(int dx, int dy);

    void setRoom(PankoRoom room);

    PankoRoom getRoom();

    boolean isSolid();

    void onCollide(PankoEntity e);

    int getWidth();

    int getHeight();

    void setLastMove(long time);

    void update();
}