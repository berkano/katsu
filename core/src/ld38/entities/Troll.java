package ld38.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import katsu.K;
import katsu.KDirection;
import katsu.KEntity;
import katsu.KTiledMapEntity;
import ld38.TrollNamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Troll extends TrollCastleEntityBase {

    public String name;

    Logger logger = LoggerFactory.getLogger(Troll.class);

    boolean psychedelic = false;
    long lastPsychMillls = System.currentTimeMillis();

    public Troll() {
        super();
        name = namer.nextName();
        logger.info("A new Troll called " + name + " appeared!");
        setSolid(true);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void update() {

        super.update();

        if (psychedelic) {
            if (lastPsychMillls < System.currentTimeMillis() - 50) {
                lastPsychMillls = System.currentTimeMillis();
                int nextC = K.random.nextInt(6);
                if (nextC == 0) getAppearance().setSpriteColour(Color.PURPLE);
                if (nextC == 1) getAppearance().setSpriteColour(Color.LIME);
                if (nextC == 2) getAppearance().setSpriteColour(Color.CYAN);
                if (nextC == 3) getAppearance().setSpriteColour(Color.GOLD);
                if (nextC == 4) getAppearance().setSpriteColour(Color.PINK);
                if (nextC == 5) getAppearance().setSpriteColour(Color.RED);
            }
        }

        int moveInterval = psychedelic ? 250 : 750;
        if (!lastMovedMoreThan(moveInterval)) return;

        if (getTargetEntity() != null) {

            KEntity target = getTargetEntity();
            KDirection suggestion = getGrid().doPathFinding(target.getGrid().getX(), target.getGrid().getY());

            if (suggestion != null) {
                tryMoveGridDirection(suggestion);
            }
        }

    }

    @Override
    public void onClick() {
        super.onClick();
        say("ugg");
    }

    public void say(String utterance) {
        utterance = utterance.replace("Mushroom","moosh");
        utterance = utterance.replace("Mine","digg");
        utterance = utterance.replace("Seed","pod");
        utterance = utterance.replace("Water","tssh");
        utterance = utterance.replace("Grass","grob");
        utterance = utterance.replace("Fish","blub");
        game.talk1.play();
        game.ui.bottomBar.writeLine("[ORANGE]"+ name + ": [GREEN]" + utterance+"[WHITE]");
    }

    public void setPsychedelic(boolean psychedelic) {
        this.psychedelic = psychedelic;
    }
}
