package mini73.entities.structures;

import katsu.K;
import katsu.KTiledMapEntity;
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
@KTiledMapEntity
public class LandingPad extends FixedItem {

    public Teleport teleport = null;
    boolean firstUpdate = true;
    private TeleportMap teleportMap = TeleportMap.instance();

    public LandingPad() {
        this.setSolid(false);
        this.setCollisionTarget(false);
    }

    @Override
    public void update() {
        super.update();

        if (firstUpdate) {
            for (Teleport t : teleportMap.teleportArrayList) {
                if (t.x == getX() / K.settings.getGridSize() && t.y == getY() / K.settings.getGridSize()) {
                    teleport = t;
                }
            }
            firstUpdate = false;
        }

    }

    @Override
    public String toString() {
        if (teleport == null) return "An inactive teleport.";
        if (teleport.link == null) return "An inactive teleport.";
        return "Teleport " + teleport.getDiscoveredName() + " linking to " + teleport.link.getDiscoveredName() + ".";
    }
}
