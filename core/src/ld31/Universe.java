package ld31;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import ld31.entities.Ship;
import panko.Panko;
import panko.PankoRoomBase;

/**
 * Created by shaun on 06/12/2014.
 */
public class Universe extends PankoRoomBase {

    private World world;

    @Override
    public void start() {
        super.start();
        this.world = new World(new Vector2(0, -10), true);

        // Add player ship
        Panko.addEntityToRoom(this, Ship.newPlayerShip(this));
    }

    @Override
    public World getWorld() {
        return world;
    }
}
