package ld33.entities;

import katsu.K;
import katsu.KDirection;
import ld33.World;

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
    private long lastTalked = System.currentTimeMillis();
    private long lastCheckedTalkCriteria = System.currentTimeMillis();

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
        this.getAppearance().setRotateSpriteOnMove(false);
        this.getAppearance().setZLayer(10);
        this.setUpdateAsRogueLike(true);
        nameMyself();
    }

    private void nameMyself() {

        List<String> genericNames = new ArrayList<String>();
        genericNames.add("Bob");
        genericNames.add("Dave");
        genericNames.add("Dave");
        genericNames.add("Dave");
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
            didLastPathFind = K.utils.currentTime();
            moveRequested(getPathFinderNextDirection());
            hasDoneFirstPathFind = true;
            setPathFinderNextDirection(null);
        } else {
            // Don't start moving randomly until we have at least been in range of the player
            if (hasDoneFirstPathFind) {
                moveRequested(KDirection.random());
            }
        }

        haveSomeChat();

    }

    private void haveSomeChat() {

        if (lastTalked > System.currentTimeMillis() - 5000) return;
        if (lastCheckedTalkCriteria > System.currentTimeMillis() - 500) return;
        lastCheckedTalkCriteria = System.currentTimeMillis();

        // Only if player is nearby
        Monster player = ((World)getRoom()).getPlayer();
        if (player == null) return;

        int dx = Math.abs(getX() - player.getX());
        int dy = Math.abs(getY() - player.getY());
        long dist = Math.round(Math.sqrt(dx * dx + dy * dy));

        if (dist > 128) return;

        String chat;

        List<String> randomQuotes = new ArrayList<String>();
        randomQuotes.add("Super creeps everywhere.");
        randomQuotes.add("She's a monster!");
        randomQuotes.add("Not tonight Josephine.");
        randomQuotes.add("Don't gaze too long into the abyss.");
        randomQuotes.add("Is that you Herobrine?");
        randomQuotes.add("Feed me!");
        randomQuotes.add("Oh, globbits!");
        randomQuotes.add("Nice day for it.");
        randomQuotes.add("I'll be back.");
        Collections.shuffle(randomQuotes);
        chat = randomQuotes.get(0);

        if (getName().equals("Dave")) {
            List<String> daveQuotes = new ArrayList<String>();
            daveQuotes.add("You remind me of the babe.");
            daveQuotes.add("I have a horror of rooms.");
            daveQuotes.add("Is that Jimmie's guitar sound?");
            daveQuotes.add("It'll be alright.");
            daveQuotes.add("I've heard a rumour from Ground Control.");
            daveQuotes.add("What's said is said.");
            Collections.shuffle(daveQuotes);
            chat = daveQuotes.get(0);
        }


        if (K.random.nextInt(8) == 0) {
            K.ui.writeText("@CYAN <" + getDisplayName() + "> " + chat);
        }

        lastTalked = System.currentTimeMillis();

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
