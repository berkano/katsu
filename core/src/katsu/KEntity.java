package katsu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by shaun on 16/11/2014.
 */
public interface KEntity {
    KEntity setX(int x);

    int getX();

    KEntity setY(int y);

    int getY();

    KEntity setTextureRegion(TextureRegion textureRegion);

    TextureRegion getTextureRegion();

    void render();

    boolean moveGrid(int dx, int dy);

    KEntity setRoom(KRoom room);

    KRoom getRoom();

    boolean isSolid();

    void onCollide(KEntity e);

    int getWidth();

    int getHeight();

    KEntity setLastMove(long time);

    long getLastMove();

    int getMaxMoveInterval();

    void setMaxMoveInterval(int interval);

    void update();

    boolean isSelected();

    void setTarget(KEntity targetEntity);

    KEntity getTarget();

    boolean overlaps(KEntity other);

    boolean wouldOverlap(KEntity other, int nx, int ny);

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

    int getzLayer();
}
