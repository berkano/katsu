package ld28.entities.structures;

import com.badlogic.gdx.Application;
import katsu.Entity;
import katsu.Settings;
import katsu.entities.FixedItem;
import ld28.Teleport;
import ld28.TeleportMap;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class LandingPad extends FixedItem {

    public Teleport teleport = null;
    boolean firstUpdate = true;

    public LandingPad() {
        this.solid = false;
        this.isCollisionTarget = false;
    }

    @Override
    public void update(Application gc) {
        super.update(gc);

        if (firstUpdate) {
            for (Teleport t : TeleportMap.teleportArrayList) {
                if (t.x == x / Settings.getTileWidth() && t.y == y / Settings.getTileHeight()) {
                    teleport = t;
                }
            }
            firstUpdate = false;
        }

    }

    @Override
    public boolean collide(Entity other) {
        return this.solid;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        if (teleport == null) return "An inactive teleport.";
        if (teleport.link == null) return "An inactive teleport.";
        return "Teleport " + teleport.getDiscoveredName() + " linking to " + teleport.link.getDiscoveredName() + ".";
    }
}
