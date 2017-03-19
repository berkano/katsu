package mini73.entities.base;

import com.badlogic.gdx.Application;
import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import katsu.*;
import mini73.NotSupportedYetException;
import mini73.Objective;
import mini73.PlaceNames;
import mini73.entities.mobs.EnemyPerson;
import mini73.entities.mobs.EnemyShip;
import mini73.entities.mobs.FriendlyPerson;
import mini73.entities.mobs.FriendlyShip;
import mini73.rooms.MainRoom;

import java.util.ArrayList;

/**
 * @author shaun
 */
public class Mob extends Mini73EntityBase {

    public long lastAttacked = 0;
    public long lastDamaged = 0;
    public String friendlyName = null;
    public long lastPathFind = 0;
    public Integer intermediateTargX = null;
    public Integer intermediateTargY = null;
    public long lastIntermedArrival = 0;
    public boolean isCollisionTarget = false;
    private Objective currentObjective;

    public Mob() {
        this.setSolid(true);
        this.isCollisionTarget = true;
        if (this instanceof EnemyShip) {
            friendlyName = PlaceNames.randomShipName();
        }
        if (this instanceof FriendlyShip) {
            friendlyName = PlaceNames.randomShipName();
        }
        if (this instanceof EnemyPerson) {
            friendlyName = PlaceNames.randomPersonName();
        }
        if (this instanceof FriendlyPerson) {
            friendlyName = PlaceNames.randomPersonName();
        }

    }

    @Override
    // TODO bug with entity appearing twice in destroy list??
    public void beforeDeath(KRoom room) {
        super.beforeDeath(room);
        K.ui.writeText(this.toString() + " died.");
    }

    @Override
    public String toString() {

        String mobName = "";

        if (friendlyName == null) {
            mobName = this.getClass().getSimpleName();
        } else {
            mobName = friendlyName;
        }
        if (this instanceof FriendlyPerson) {
            mobName += ". "+((FriendlyPerson)this).tradesAsString();
        }
        return mobName;
    }

    @Override
    public void update() {
        super.update();    //To change body of overridden methods use File | Settings | File Templates.

        if (currentObjective != Objective.NOTHING) {
            if (getTargetEntity() == null || getTargetEntity().isDestroyed()) {
                currentObjective = Objective.NOTHING;
                setTargetEntity(null);
            }
        }

        if (currentObjective == Objective.MOVE) {
            throw new NotSupportedYetException();
        }

    }
}
