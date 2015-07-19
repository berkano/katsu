package ld32.entities;

import ld32.LD32Sounds;
import ld32.World;
import katsu.K;
import katsu.KEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class WayPoint extends SolidEntity {

    public WayPoint() {
        super();
        setzLayer(1);
    }

    @Override
    public void onCollide(KEntity other) {
        if (other instanceof Mole) {
            if (getHealth() > 0) {
                this.setHealth(0);
                World.setWaypointX(getX());
                World.setWaypointY(getY());
                getRoom().getDeadEntities().add(this);
                K.getUI().writeText("Waypoint reached! Restarting the level will continue from here.");
                LD32Sounds.waypoint.play();
            }
        }
        super.onCollide(other);
    }
}
