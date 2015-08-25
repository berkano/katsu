package ld33.entities;

/**
 * Created by shaun on 22/08/2015.
 */
public class Warrior extends NPC {

    public Warrior() {
        super();
        getStats().jumpToLevel(3);
    }
}
