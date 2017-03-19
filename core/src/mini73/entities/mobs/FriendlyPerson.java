package mini73.entities.mobs;

import katsu.K;
import mini73.GameState;
import mini73.Util;
import mini73.entities.base.FriendlyMob;
import mini73.entities.resources.*;
import mini73.rooms.MainRoom;

import java.util.HashMap;

/**
 * @author shaun
 */
public class FriendlyPerson extends FriendlyMob {

    public HashMap<Class, Integer> trades = new HashMap<Class, Integer>();
    public int profitMargin = 5 + K.random.nextInt(10);

    public FriendlyPerson() {
        super();
        for (int i = 1; i < 10; i++) {
            if (i == 1) trades.put(Diamond.class, Util.randomInRange(1000, 2000));
            if (i == 2) trades.put(Wood.class, Util.randomInRange(10, 20));
            if (i == 3) trades.put(Gold.class, Util.randomInRange(500, 750));
            if (i == 4) trades.put(Iron.class, Util.randomInRange(100, 150));
            if (i == 5) trades.put(LuxuryItem.class, Util.randomInRange(500, 3000));
            if (i == 6) trades.put(Medicine.class, Util.randomInRange(250, 350));
            if (i == 7) trades.put(Fuel.class, Util.randomInRange(100, 200));
            if (i == 8) trades.put(Robot.class, Util.randomInRange(3000, 6000));
            if (i == 9) trades.put(Potato.class, Util.randomInRange(25, 75));
        }
    }

    public String tradesAsString() {
        String result = "";
        int itemNumber = 0;
        for (Class c : trades.keySet()) {
            itemNumber++;
            String itemName = c.getSimpleName();
            result += String.format("[%s] %s: %s/%s, ", itemNumber, itemName, buyPriceFor(c), sellPriceFor(c));
        }
        return result;
    }

    public void tryTrade(int tradeNum) {
        MainRoom thisRoom = (MainRoom)getRoom();
        GameState gameState = thisRoom.gameState;
        if (gameState.inventory != null) {
            gameState.credits += buyPriceFor(gameState.inventory);
            K.ui.writeText("Sold!");
            gameState.inventory = null;
        } else {
            int neededCredits = sellPriceFor(tradeClassByIndex(tradeNum));
            if (gameState.credits < neededCredits) {
                K.ui.writeText("Not enough credits!");
            } else {
                K.ui.writeText("Bought!");
                gameState.credits -= neededCredits;
                gameState.inventory = tradeClassByIndex(tradeNum);
            }
        }
    }

    public Class tradeClassByIndex(int i) {
        int co = 0;
        for (Class c : trades.keySet()) {
            co++;
            if (co == i) return c;
        }
        return null;
    }

    public int buyPriceFor(Class c) {
        return trades.get(c);
    }

    public int sellPriceFor(Class c) {
        return ((100+profitMargin)*trades.get(c)) / 100;
    }

}
