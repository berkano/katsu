package mini73.entities.terrain;

import katsu.KTiledMapEntity;
import mini73.entities.base.Terrain;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 28/04/13
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
@KTiledMapEntity
public class Fire extends Terrain {

    double lightLevel = 0d;

    public Fire() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
        //this.color = Color.DARK_GRAY;
    }


}
