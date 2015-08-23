package ld33.entities;

import katsu.K;
import katsu.KEntity;
import katsu.KEntityBase;
import ld33.Stats;

/**
 * Created by shaun on 22/08/2015.
 */
public class MobBase extends KEntityBase {

    private Stats stats = new Stats();
    private long lastHealthIncrement = System.currentTimeMillis();

    public MobBase() {
        super();
        this.setFlipSpriteOnMove(true);
        this.setzLayer(10);
    }

    public boolean isEnemy(KEntity other) {

        if (!(this instanceof Monster)) {
            if (other instanceof Monster) {
                Monster monster = (Monster)other;
                if (!monster.isLooksHuman()) {
                    return true;
                }
            }
        }

        if ((this instanceof Monster)) {
            Monster monster = (Monster) this;
            if (!monster.isLooksHuman()) {
                if (other instanceof MobBase) {
                    if (!(other instanceof Monster)) return true;
                }
            }
        }

        return false;
    }

    @Override
    public void onCollide(KEntity other) {
        super.onCollide(other);
        if (other instanceof MobBase) {
            if (isEnemy(other)) {
                String thisStr = this.getClass().getSimpleName();
                String thatStr = other.getClass().getSimpleName();
//                K.getUI().writeText(thisStr + " attacks " + thatStr + "!");
                Combat.run(this, (MobBase)other);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (lastHealthIncrement != K.getLastRogueUpdate()) {
            lastHealthIncrement = K.getLastRogueUpdate();
            if (K.random.nextInt(3) == 1) {
                getStats().addHealth(1 + getStats().getLevel());
            }
        }
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
