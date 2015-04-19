package panko;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld32.entities.EmptyDirt;

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

    long getLastMove();

    int getMaxMoveInterval();

    void setMaxMoveInterval(int interval);

    void update();

    boolean isSelected();

    void setTarget(PankoEntity targetEntity);

    PankoEntity getTarget();

    boolean overlaps(PankoEntity other);

    boolean wouldOverlap(PankoEntity other, int nx, int ny);

    int getGridX();

    int getGridY();

    void setBeingRemoved(boolean beingRemoved);

    boolean isBeingRemoved();

    void onMoved();

    int getHealth();

    void setHealth(int health);

    int getMaxHealth();

    void setMaxHealth(int maxHealth);

    void createInPlace(Class clazz);

    boolean canCollideWith(Class clazz);
}
