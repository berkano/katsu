package mini73.entities.mobs;

import katsu.K;
import katsu.KTiledMapEntity;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@KTiledMapEntity
public class PlayerPerson extends FriendlyMob {

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void lookAtMe() {

        K.graphics.camera.position.x = getX() + getHeight() / 2 + K.random.nextFloat() / 10;
        K.graphics.camera.position.y = getY() + getHeight() / 2 + K.random.nextFloat() / 10;
        K.graphics.camera.rotate(0.01f);
        K.graphics.camera.zoom = 0.01f + K.random.nextFloat() / 1000f + (float)ageMillis() / 30000f;

    }

    @Override
    public void render() {
        super.render();
    }

    public PlayerPerson() {
        this.orientSpriteByMovement = false;
    }

    @Override
    public String toString() {
        return "Major Tim";
    }
}
