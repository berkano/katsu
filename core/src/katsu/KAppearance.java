package katsu;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 17/07/2016.
 */
public class KAppearance {

    @Getter @Setter private TextureRegion textureRegion;
    @Getter @Setter private KEntity entity;
    @Getter @Setter private Sprite sprite;
    @Getter @Setter private int spriteRotation = 0;
    @Getter @Setter private float spriteScale = 1.0f;
    @Getter @Setter private boolean flipSpriteOnMove = false;
    @Getter @Setter private boolean spriteFlip = false;
    @Getter @Setter private boolean rotateSpriteOnMove = true;
    @Getter @Setter private int zLayer = 0;

    public KAppearance(KEntity entity) {
        this.entity = entity;
    }

    public void render() {
        if (textureRegion == null) {
            textureRegion = K.textureCache.get(entity.getClass());
        }
        float xScale = spriteScale;
        float yScale = spriteScale;
        if (isSpriteFlip()) {
            xScale = -xScale;
        }
        K.graphics.activeSpriteBatch.draw(
                textureRegion,
                entity.getX(), entity.getY(), entity.getWidth() / 2, entity.getHeight() / 2,
                entity.getWidth(), entity.getHeight(),
                xScale, yScale, (float) spriteRotation
        );
    }

    public void juiceMySprite(float juiceiness) {
        this.setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(15) - 7);
        this.setSpriteScale(K.random.nextFloat() * juiceiness + 1.0f + juiceiness);
    }

    public void setSpriteForDirection(KDirection direction) {

        if (isRotateSpriteOnMove()) {
            setSpriteRotation(direction.getRotation());
        }
        if (isFlipSpriteOnMove()) {
            if (direction.equals(KDirection.LEFT)) {
                setSpriteFlip(true);
            }
            if (direction.equals(KDirection.RIGHT)) {
                setSpriteFlip(false);
            }
        }

    }

    public void setTextureFrom(Class clazz) {
        setTextureRegion(K.textureCache.get(clazz));
    }

    public void applyTextureFor(Class clazz) {
        setTextureRegion(K.textureCache.get(clazz));
    }
}
