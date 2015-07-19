package ld32.entities;

import ld32.LD32Sounds;
import ld32.World;
import katsu.Panko;
import katsu.PankoEntity;

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
                Panko.getUI().writeText("Waypoint reached! Restarting the level will continue from here.");
                LD32Sounds.waypoint.play();
            }
        }
        super.onCollide(other);
    }
}
