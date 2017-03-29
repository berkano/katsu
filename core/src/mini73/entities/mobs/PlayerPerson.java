package mini73.entities.mobs;

import katsu.TiledMapEntity;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@TiledMapEntity
public class PlayerPerson extends FriendlyMob {

    @Override
    public void update() {
        super.update();
        lookAtMe();
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
