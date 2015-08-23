package ld33.entities;

import katsu.KDirection;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class Warrior extends NPC {

    public Warrior() {
        super();
        getStats().jumpToLevel(3);
    }
}
