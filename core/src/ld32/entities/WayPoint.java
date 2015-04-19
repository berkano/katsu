package ld32.entities;

import ld32.World;
import panko.Panko;
import panko.PankoEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class WayPoint extends SolidEntity {

    @Override
    public void onCollide(PankoEntity other) {
        if (other instanceof Mole) {
            if (getHealth() > 0) {
                this.setHealth(0);
                World.setWaypointX(getX());
                World.setWaypointY(getY());
                getRoom().getDeadEntities().add(this);
                Panko.getUI().writeText("Waypoint reached!");
            }
        }
        super.onCollide(other);
    }
}
