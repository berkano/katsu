package ld28.entities.mobs;

import katsu.Katsu;
import katsu.Util;
import ld28.GameParameters;
import ld28.GameState;
import ld28.entities.base.FriendlyMob;
import ld28.entities.resources.*;
import ld28.rooms.MainRoom;

import java.util.HashMap;

/**
 * @author shaun
 */
public class FriendlyPerson extends FriendlyMob {

    public HashMap<Class, Integer> trades = new HashMap<Class, Integer>();
    public int profitMargin = 5 + Katsu.random.nextInt(10);

    public FriendlyPerson() {
        super();
        for (int i = 1; i < 10; i++) {
            if (i == 1) trades.put(Diamond.class, Util.randomInRange(GameParameters.diamondMinCost, GameParameters.diamondMaxCost));
            if (i == 2) trades.put(Wood.class, Util.randomInRange(GameParameters.woodMinCost, GameParameters.woodMaxCost));
            if (i == 3) trades.put(Gold.class, Util.randomInRange(GameParameters.goldMinCost, GameParameters.goldMaxCost));
            if (i == 4) trades.put(Iron.class, Util.randomInRange(GameParameters.ironMinCost, GameParameters.ironMaxCost));
            if (i == 5) trades.put(LuxuryItem.class, Util.randomInRange(GameParameters.luxuryMinCost, GameParameters.luxuryMaxCost));
            if (i == 6) trades.put(Medicine.class, Util.randomInRange(GameParameters.medicineMinCost, GameParameters.medicineMaxCost));
            if (i == 7) trades.put(Fuel.class, Util.randomInRange(GameParameters.fuelMinCost, GameParameters.fuelMaxCost));
            if (i == 8) trades.put(Robot.class, Util.randomInRange(GameParameters.robotMinCost, GameParameters.robotMaxCost));
            if (i == 9) trades.put(Potato.class, Util.randomInRange(GameParameters.potatoMinCost, GameParameters.potatoMaxCost));
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
        GameState gameState = ((MainRoom)room).gameState;
        if (gameState.inventory != null) {
            gameState.credits += buyPriceFor(gameState.inventory);
            ui.writeText("Sold!");
            gameState.inventory = null;
        } else {
            int neededCredits = sellPriceFor(tradeClassByIndex(tradeNum));
            if (gameState.credits < neededCredits) {
                ui.writeText("Not enough credits!");
            } else {
                ui.writeText("Bought!");
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
