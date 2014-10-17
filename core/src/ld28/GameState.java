package ld28;

import ld28.rooms.MainRoom;

/**
 * Created by shaun on 14/12/13.
 */
public class GameState {

    public int fuel = GameParameters.startFuel;

    public MainRoom room;

    public Class inventory = null;
    public long credits = GameParameters.startCredits;

    public GameState(MainRoom room) {
        this.room = room;
    }

    public String statusBar() {
        int playerHealth = 0;
        int shipHealth = 0;
        if (room.player != null) {
            playerHealth = room.player.health;
        }
        if (room.ship != null) {
            shipHealth = room.ship.health;
        }

        return String.format("Coords: %s, %s | Credits: %s | Inventory: %s | Fuel: %s | Health: %s, %s", room.ship.x/16, room.ship.y/16, credits, inventoryAsString(), fuel, playerHealth, shipHealth);
    }

    public String inventoryAsString() {
        if (inventory != null) {
            return inventory.getSimpleName();
        } else {
            return "None";
        }
    }
}
