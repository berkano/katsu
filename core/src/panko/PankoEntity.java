package panko;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

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

}
