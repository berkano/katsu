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
        return 5 + level * 2;
    }

    public int getStrength() {
        return 5 + level * 3;
    }

    public void damage(int amount) {
        health -= amount;
    }

    public int getSkill() {
        return 1 + level * 2;
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
        if (xp >= 10 && level < 3) return 3;
        if (xp >= 25 && level < 4) return 4;
        if (xp >= 50 && level < 5) return 5;
        if (xp >= 100 && level < 6) return 6;
        if (xp >= 250 && level < 7) return 7;
        if (xp >= 500 && level < 8) return 8;
        if (xp >= 1000 && level < 9) return 9;
        if (xp >= 2500 && level < 10) return 10;
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
}
