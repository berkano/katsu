package panko;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 16/11/2014.
 */
public interface PankoEntity {
    PankoEntity setX(int x);

    int getX();

    PankoEntity setY(int y);

    int getY();

    PankoEntity setTextureRegion(TextureRegion textureRegion);

    TextureRegion getTextureRegion();

    void render();

    boolean moveGrid(int dx, int dy);

    PankoEntity setRoom(PankoRoom room);

    PankoRoom getRoom();

    boolean isSolid();

    void onCollide(PankoEntity e);

    int getWidth();

    int getHeight();

    PankoEntity setLastMove(long time);

    void update();

    boolean isSelected();
}
