package mini73.entities.base;

import katsu.KEntity;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 28/04/13
 * Time: 09:47
 * To change this template use File | Settings | File Templates.
 */
public class Terrain extends Mini73EntityBase {

    public boolean hasPickedTile = false;

    public Terrain() {
        this.setSolid(false);
    }

}