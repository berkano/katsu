package ld33;

/**
 * Created by shaun on 23/08/2015.
 */
public class Stats {

    private int level = 0;
    private int xp = 0;
    private int health = Stats.maxHealthForLevel(level);

    @Override
    public String toString() {
        return "Health: " + health + " XP: " + xp + " Level: " + level;
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
        return 5;
    }

    public int getStrength() {
        return 5;
    }

    public void damage(int amount) {
        health -= amount;
    }

    public int getSkill() {
        return 1;
    }

    public void fullHealth() {
        health = maxHealthForLevel(level);
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public int levelUpAvailable() {
        if (xp >= 2 && level < 1) return 1;
        if (xp >= 5 && level < 2) return 2;
        return 0;
    }

    public void levelUp() {
        if (levelUpAvailable() > 0) {
            level = levelUpAvailable();
        }
    }

}
