package ld33.entities;

import katsu.K;
import katsu.KDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shaun on 22/08/2015.
 */
public class NPC extends MobBase {

    private long didLastPathFind;
    private boolean hasDoneFirstPathFind = false;
    private String name;
    private String title;

    public boolean isHasDoneFirstPathFind() {
        return hasDoneFirstPathFind;
    }

    public void setHasDoneFirstPathFind(boolean hasDoneFirstPathFind) {
        this.hasDoneFirstPathFind = hasDoneFirstPathFind;
    }

    public NPC()
    {
        super();
        this.setSolid(true);
        this.setRotateSpriteOnMove(false);
        this.setzLayer(10);
        this.setUpdateAsRogueLike(true);
        nameMyself();
    }

    private void nameMyself() {

        List<String> genericNames = new ArrayList<String>();
        genericNames.add("Bob");
        genericNames.add("Dave");
        genericNames.add("Steve");
        genericNames.add("Jim");
        genericNames.add("Seth");
        genericNames.add("Frank");
        genericNames.add("Pete");
        genericNames.add("Egbert");
        genericNames.add("Horace");
        genericNames.add("Vince");
        genericNames.add("Howard");
        genericNames.add("Sophie");
        genericNames.add("Kate");
        genericNames.add("Violet");
        genericNames.add("Mike");
        genericNames.add("Sally");
        genericNames.add("Penelope");
        genericNames.add("Arthur");
        genericNames.add("Archibald");
        genericNames.add("Archibald");
        genericNames.add("Josephine");
        genericNames.add("Florence");

        Collections.shuffle(genericNames);
        setName(genericNames.get(0));

        List<String> genericTitles = new ArrayList<String>();
        genericTitles.add("Esq.");
        genericTitles.add("Lord");
        genericTitles.add("Sir");
        genericTitles.add("Lady");
        genericTitles.add("Prof.");

        Collections.shuffle(genericTitles);

        if (K.random.nextInt(3) == 0) {
            setTitle(genericTitles.get(0));
        }

    }

    @Override
    public void update() {

        super.update();

        if (getPathFinderNextDirection() != null) {
            didLastPathFind = K.currentTime();
            moveRequested(getPathFinderNextDirection());
            hasDoneFirstPathFind = true;
            setPathFinderNextDirection(null);
        } else {
            // Don't start moving randomly until we have at least been in range of the player
            if (hasDoneFirstPathFind) {
                moveRequested(KDirection.random());
            }
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        String result = name;
        if (title != null) {
            result = title + " " + name;
        }
        result += " the " + getClass().getSimpleName();
        return result;
    }
}
