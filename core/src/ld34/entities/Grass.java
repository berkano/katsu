package ld34.entities;

/**
 * Created by shaun on 12/12/2015.
 */
public class Grass extends LD34EntityBase {

    public Grass() {
        super();
        getAppearance().juiceMySprite(0.1f);
        getAppearance().setZLayer(-100);
    }

}
