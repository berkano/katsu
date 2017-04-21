package ld38.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import katsu.K;
import katsu.KEntity;
import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Player extends KEntity {

    float zoom = 0.2f;

    @Override
    public void update() {
        super.update();
        lookAtMe();
        OrthographicCamera camera = K.graphics.camera;
        zoom += 0.001f;
        camera.zoom = zoom;
        camera.rotate(0.03f);
    }
}
