package ld38.entities;

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

        if (!lastMovedMoreThan(250)) return;

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
        game.talk1.play();
        game.ui.bottomBar.writeLine("[ORANGE]"+ name + "[WHITE] says [GREEN]'" + utterance+"'[WHITE].");
    }
}
