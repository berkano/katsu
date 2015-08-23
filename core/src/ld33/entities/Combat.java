package ld33.entities;

import katsu.K;
import katsu.KEntity;
import ld33.World;

import java.util.List;

/**
 * Created by shaun on 23/08/2015.
 */
public class Combat {

    public static void run(MobBase attacker, MobBase enemy) {

        boolean isPlayerAttack = false;
        World world = (World) attacker.getRoom();
        if (attacker instanceof Monster) isPlayerAttack = true;

        int attack1 = attacker.getStats().getStrength();
        int attack2 = enemy.getStats().getStrength();

        int effectiveAttack = attack1 - attack2;
        if (effectiveAttack <= 0) effectiveAttack = 1;

        if (K.random.nextBoolean()) {
            effectiveAttack *= 2;
        }

        String attackerName = attacker.getClass().getSimpleName();
        String enemyName = enemy.getClass().getSimpleName();

        boolean attackSuccessful = false;
        int skill1 = attacker.getStats().getSkill();
        int skill2 = enemy.getStats().getSkill();
        int totalSkill = skill1 + skill2;
        int roll = K.random.nextInt(totalSkill);
        if (roll < skill1) attackSuccessful = true;
        String colourCode = "";

        if (isPlayerAttack) {
            world.setLastMobAttackedByPlayer(enemy);
        }

        if (attackSuccessful) {
            if (isPlayerAttack) {
                colourCode = "@GREEN ";
            } else {
                colourCode = "@PINK ";
            }
            K.getUI().writeText(colourCode + attackerName + " attacks " + enemyName + " with damage " + effectiveAttack + "!");
            enemy.getStats().damage(effectiveAttack);
            if (enemy.getStats().getHealth() <= 0) {
                int livesLeft = enemy.getStats().getLives() - 1;
                if (livesLeft < 0) livesLeft = 0;
                K.getUI().writeText("@RED " + enemyName + " dies! ("+livesLeft + " lives left)");
                if (isPlayerAttack) {
                    world.setLastMobAttackedByPlayer(null);
                }
                // TODO respawn them - for now just reset health
                if (enemy.getStats().getLives() == 0) {
                    K.getUI().writeText("@RED *** PERMADEATH!! ***");
                    enemy.destroy();
                } else {
                    enemy.getStats().setLives(enemy.getStats().getLives() - 1);
                    enemy.getStats().fullHealth();
                    teleportToNearestBed(enemy);
                }

                // XP earned
                int xp = 1 + (enemy.getStats().getXp() / 3);
                attacker.getStats().addXp(xp);
                int newLevel = attacker.getStats().levelUpAvailable();
                if (newLevel > 0) {
                    K.getUI().writeText("@YELLOW " + attackerName + " rises to level " + newLevel + "!");
                    attacker.getStats().levelUp();
                }
            }
        } else {
            colourCode = "@LIGHT_GRAY ";
            K.getUI().writeText(colourCode + attackerName + " swings for " + enemyName + " but misses!");
        }
    }

    private static void teleportToNearestBed(MobBase mob) {

        Bed nearestBed = null;
        long nearestBedDistance = 99999999;
        List<KEntity> entities = mob.getRoom().getEntities();
        for (KEntity e : entities) {
            if (e instanceof Bed) {
                Bed bed = (Bed) e;
                int dx = Math.abs(mob.getX() - bed.getX());
                int dy = Math.abs(mob.getY() - bed.getY());
                long dist = Math.round(Math.sqrt(dx * dx + dy * dy));
                if (dist < nearestBedDistance) {
                    nearestBedDistance = dist;
                    nearestBed = bed;
                }
            }
        }

        if (nearestBed != null) {
            mob.setX(nearestBed.getX());
            mob.setY(nearestBed.getY());
            if (mob instanceof Monster) {
                Monster monster = (Monster) mob;
                monster.setLooksHuman(true);
            }
        } else {
            K.getUI().writeText("Error - could not find nearest bed for " + mob.getClass().getSimpleName());
        }


    }

}
