package mini73.entities.structures;

import com.badlogic.gdx.Application;
import katsu.KEntity;
import mini73.Teleport;
import mini73.TeleportMap;
import mini73.entities.base.FixedItem;

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
        this.setSolid(false);
        this.setCollisionTarget(false);
    }

    @Override
    public void update(Application gc) {
        super.update(gc);

        if (firstUpdate) {
            for (Teleport t : TeleportMap.teleportArrayList) {
                if (t.x == x / Settings.tileWidth && t.y == y / Settings.tileHeight) {
                    teleport = t;
                }
            }
            firstUpdate = false;
        }

    }

    @Override
    public boolean collide(KEntity other) {
        return this.solid;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        if (teleport == null) return "An inactive teleport.";
        if (teleport.link == null) return "An inactive teleport.";
        return "Teleport " + teleport.getDiscoveredName() + " linking to " + teleport.link.getDiscoveredName() + ".";
    }
}
