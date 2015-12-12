package ld34.entities;

import katsu.KLog;

/**
 * Created by shaun on 12/12/2015.
 */
public class Snowman extends LD34EntityBase {

    @Override
    public void render() {

        //KLog.trace("Snowman instance " + toString() + " rendering");

        lookAtMe();
        super.render();
    }
}
