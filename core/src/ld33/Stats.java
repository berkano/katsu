package ld33;

import java.util.HashMap;

/**
 * Created by shaun on 23/08/2015.
 */
public class Stats {

    private int level = 0;
    private int xp = 0;
    private int health = Stats.maxHealthForLevel(level);

    public static HashMap<Integer, Integer> levelXPs = new HashMap<Integer, Integer>();

    static {
        levelXPs.put(0, 0);
        levelXPs.put(1, 2);
        levelXPs.put(2, 5);
        levelXPs.put(3, 10);
        levelXPs.put(4, 25);
        levelXPs.put(5, 50);
        levelXPs.put(6, 100);
        levelXPs.put(7, 250);
        levelXPs.put(8, 500);
        levelXPs.put(9, 1000);
        levelXPs.put(10, 2500);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    private int startLives = 3;
    private int lives = startLives;

    @Override
    public String toString() {
        return "Health: " + health + " XP: " + xp + " Level: " + level + " Lives left: " + lives + "/"+startLives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    private static int maxHealthForLevel(int level) {
        return 10 + level * 2;
    }

    public int getStrength() {
        return 1 + (level * 3) / 2;
    }

    public void damage(int amount) {
        health -= amount;
    }

    public int getSkill() {
        return 1 + level;
    }

    public void fullHealth() {
        health = maxHealthForLevel(level);
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public int levelUpAvailable() {
        if (xp >= levelXPs.get(1) && level < 1) return 1;
        if (xp >= levelXPs.get(2) && level < 2) return 2;
        if (xp >= levelXPs.get(3) && level < 3) return 3;
        if (xp >= levelXPs.get(4) && level < 4) return 4;
        if (xp >= levelXPs.get(5) && level < 5) return 5;
        if (xp >= levelXPs.get(6) && level < 6) return 6;
        if (xp >= levelXPs.get(7) && level < 7) return 7;
        if (xp >= levelXPs.get(8) && level < 8) return 8;
        if (xp >= levelXPs.get(9) && level < 9) return 9;
        if (xp >= levelXPs.get(10) && level < 10) return 10;
        return 0;
    }

    public void levelUp() {
        if (levelUpAvailable() > 0) {
            level = levelUpAvailable();
        }
    }

    public void addHealth(int amount) {
        health += amount;
        if (health > maxHealthForLevel(level)) health = maxHealthForLevel(level);
    }

    public void jumpToLevel(int level) {
        setLevel(level);
        setXp(levelXPs.get(level));
    }
}
