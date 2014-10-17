package katsu.entities;

import katsu.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 28/04/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class Terrain extends Entity {

    public boolean hasPickedTile = false;

    public Terrain() {
        this.solid = false;
    }

    @Override
    public boolean collide(Entity other) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
