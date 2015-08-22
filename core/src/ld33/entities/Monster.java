package ld33.entities;

import katsu.K;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class Monster extends KEntityBase {

    @Override
    public void render() {
        super.render();
        lookAtMe();
    }

}
