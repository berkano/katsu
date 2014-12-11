package ld31v2.entities;

import ld31v2.Tuning;
import ld31v2.WarGame;
import panko.Panko;
import panko.PankoEntity;
import panko.PankoEntityBase;

import java.util.ArrayList;

/**
 * Created by shaun on 06/12/2014.
 */
public class Spawner extends PankoEntityBase {

    private long lastSpawnMillis = 0;
    private long lastTriedSpawn = 0;
    private long timeSpawningBecameViable = 0;

    @Override
    public void update() {
        super.update();

        spawnMobIfRightConditions();

    }

    private void spawnMobIfRightConditions() {

        // Don't try to spawn too often.
        if (lastTriedSpawn > System.currentTimeMillis() - 1000) return;
        lastTriedSpawn = System.currentTimeMillis();

        Class newMobClass = determineMobToSpawn();

        if (newMobClass != null) {
            // Don't spawn immediately - conditions must be right for a minimum period of time.
            if (timeSpawningBecameViable == 0) {
                timeSpawningBecameViable = System.currentTimeMillis();
                return;
            } else {
                if (timeSpawningBecameViable > System.currentTimeMillis() - Tuning.majorityTimeNeededForSpawn) return;
            }

            timeSpawningBecameViable = 0;

            try {
                Mob mob = (Mob)newMobClass.newInstance();
                mob.setX(getX());
                mob.setY(getY());
                Panko.queueEntityToRoom(getRoom(), mob);
                Panko.queueEntityToTop(mob);
                if (mob instanceof SoldierP1) {
                    WarGame.spawn.play();
                }
                lastSpawnMillis = System.currentTimeMillis();
            } catch (Exception ex) {

            }

        }

    }

    private Class determineMobToSpawn() {

        if (lastSpawnMillis > System.currentTimeMillis() - Tuning.maxSpawnInterval) return null;

        ArrayList<PankoEntity> neighbouringEntities = new ArrayList<PankoEntity>();

        int gridX = getGridX();
        int gridY = getGridY();

        for (PankoEntity e : getRoom().getEntities()) {
            int eGridX = e.getGridX();
            int eGridY = e.getGridY();

            int dx = Math.abs(eGridX - gridX);
            int dy = Math.abs(eGridY - gridY);

            if (dx <= 1 && dy <= 1) {
                neighbouringEntities.add(e);
            }

            // Check if a mob is already on the spawner pad.
            if (dx == 0 && dy == 0) {
                if (e instanceof Mob) {
                    return null;
                }
            }

        }

        int player1Mobs = 0;
        int player2Mobs = 0;
        int player3Mobs = 0;
        int player4Mobs = 0;
        int player5Mobs = 0;
        int player6Mobs = 0;
        int mostMobs = 0;

        for (PankoEntity e : neighbouringEntities) {
            if (e instanceof SoldierP1) player1Mobs++;
            if (e instanceof SoldierP2) player2Mobs++;
            if (e instanceof SoldierP3) player3Mobs++;
            if (e instanceof SoldierP4) player4Mobs++;
            if (e instanceof SoldierP5) player5Mobs++;
            if (e instanceof SoldierP6) player6Mobs++;
        }

        int secondMostMobs = 0;
        if (player1Mobs > mostMobs) { mostMobs = player1Mobs; secondMostMobs = mostMobs; }
        if (player2Mobs > mostMobs) { mostMobs = player2Mobs; secondMostMobs = mostMobs; }
        if (player3Mobs > mostMobs) { mostMobs = player3Mobs; secondMostMobs = mostMobs; }
        if (player4Mobs > mostMobs) { mostMobs = player4Mobs; secondMostMobs = mostMobs; }
        if (player5Mobs > mostMobs) { mostMobs = player5Mobs; secondMostMobs = mostMobs; }
        if (player6Mobs > mostMobs) { mostMobs = player6Mobs; secondMostMobs = mostMobs; }

        // Player must have majority in the base
        if (player1Mobs == mostMobs && secondMostMobs < player1Mobs) { return SoldierP1.class; }
        if (player2Mobs == mostMobs && secondMostMobs < player2Mobs) { return SoldierP2.class; }
        if (player3Mobs == mostMobs && secondMostMobs < player3Mobs) { return SoldierP3.class; }
        if (player4Mobs == mostMobs && secondMostMobs < player4Mobs) { return SoldierP4.class; }
        if (player5Mobs == mostMobs && secondMostMobs < player5Mobs) { return SoldierP5.class; }
        if (player6Mobs == mostMobs && secondMostMobs < player6Mobs) { return SoldierP6.class; }

        return null;

    }
}
