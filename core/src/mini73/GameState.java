package mini73;

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

    public String statusBar() {
        int playerHealth = 0;
        int shipHealth = 0;
        if (room.player != null) {
            playerHealth = room.player.getHealth();
        }
        if (room.ship != null) {
            shipHealth = room.ship.getHealth();
        }

        return String.format("Coords: %s, %s | Credits: %s | Inventory: %s | Fuel: %s | Health: %s, %s", room.ship.getX()/16, room.ship.getY()/16, credits, inventoryAsString(), fuel, playerHealth, shipHealth);
    }

    public String inventoryAsString() {
        if (inventory != null) {
            return inventory.getSimpleName();
        } else {
            return "None";
        }
    }
}
