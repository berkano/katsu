package mini73;

import mini73.entities.mobs.PlayerPerson;
import mini73.entities.mobs.Ship;
import mini73.rooms.MainRoom;

/**
 * Created by shaun on 14/12/13.
 */
public class GameState {

    public int fuel = 1000;

    public MainRoom room;

    public Class inventory = null;
    public long credits = 1000;

    public GameState(MainRoom room) {
        this.room = room;
    }

    public String statusBarText() {
        int playerHealth = 0;
        int shipHealth = 0;
        if (room.player != null) {
            playerHealth = room.player.getHealth();
        }
        if (room.ship != null) {
            shipHealth = room.ship.getHealth();
        }

        String following = "UNKNOWN";
        if (room.following instanceof Ship) {
            following = "Ship";
        }
        if (room.following instanceof PlayerPerson) {
            following = "Major Tim";
        }


        int xCoord = -1;
        int yCoord = -1;
        if (room.ship != null) {
            xCoord = room.ship.getX()/16;
            yCoord = room.ship.getY()/16;
        }
        return String.format(following  + ": coords: %s, %s | Credits: %s | Inventory: %s | Fuel: %s | Health: %s, %s", xCoord, yCoord, credits, inventoryAsString(), fuel, playerHealth, shipHealth);
    }

    public String inventoryAsString() {
        if (inventory != null) {
            return inventory.getSimpleName();
        } else {
            return "None";
        }
    }
}
