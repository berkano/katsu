package ld34.entities;

/**
 * Created by shaun on 12/12/2015.
 */
public class Dirt extends LD34EntityBase {

    public Dirt() {
        super();
        getAppearance().juiceMySprite(0.1f);
        getAppearance().setZLayer(-100);
    }

}
