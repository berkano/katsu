package ld38.entities;

import com.badlogic.gdx.graphics.Color;
import katsu.K;
import katsu.KDirection;
import katsu.KEntity;
import katsu.KTiledMapEntity;
import ld38.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Troll extends TrollCastleEntityBase {

    public String name;

    Logger logger = LoggerFactory.getLogger(Troll.class);

    boolean psychedelic = false;
    long lastPsychMillls = System.currentTimeMillis();
    long startPyschMillis = 0;
    public boolean hasHadPsychedelics = false;
    Map map;

    public Troll() {
        super();
        name = namer.nextName();
        logger.info("A new Troll called " + name + " appeared!");
        setSolid(true);
        getAppearance().setSpriteScale(K.random.nextFloat() * 1.5f + 0.5f);
        juiceRotation();
        getAppearance().setRotateSpriteOnMove(false);
    }

    private void juiceRotation() {
        getAppearance().setSpriteRotation(K.random.nextInt(15) - 7);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void update() {

        super.update();

        map = (Map)getRoom();

        if (psychedelic) {
            if (startPyschMillis < System.currentTimeMillis() - 10000) {
                setPsychedelic(false);
                getAppearance().setSpriteColour(Color.WHITE);
            }
        }

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
                if (K.random.nextInt(100) == 3){
                    int w = K.random.nextInt(game.waffle.size());
                    say(game.waffle.get(w));
                }
            }
        }

        int moveInterval = psychedelic ? 250 : 750;
        if (!lastMovedMoreThan(moveInterval)) return;

        if (getTargetEntity() != null) {

            KEntity target = getTargetEntity();
            if (hasHadPsychedelics) {
                map.makeWaterPassable();
            }
            KDirection suggestion = getGrid().doPathFinding(target.getGrid().getX(), target.getGrid().getY());

            if (suggestion != null) {
                boolean result = tryMoveGridDirection(suggestion);
                if (result == true) {
                    game.walk.play();
                    juiceRotation();
                }
            }

            map.makeWaterSolid();

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
        utterance = utterance.replace("Water","splish");
        utterance = utterance.replace("Grass","grob");
        utterance = utterance.replace("Fish","blubby");
        utterance = utterance.replace("Sand","grund");
        utterance = utterance.replace("Wall","wol");
        utterance = utterance.replace("Tower","toor");
        utterance = utterance.replace("Stone","rok");
        utterance = utterance.replace("Gold","guld");
        int talkSound = K.random.nextInt(4);

        if (talkSound == 0) game.talk1.play();
        if (talkSound == 1) game.talk2.play();
        if (talkSound == 2) game.talk3.play();
        if (talkSound == 3) game.talk4.play();

        game.ui.bottomBar.writeLine("[ORANGE]"+ toString() + "[GRAY]: [GREEN]" + utterance+"[WHITE]");
    }

    public void setPsychedelic(boolean psychedelic) {
        this.psychedelic = psychedelic;
        if (psychedelic == true) {
            startPyschMillis = System.currentTimeMillis();
            hasHadPsychedelics = true;
        }
    }

    public void mine() {

        getAppearance().setVisible(false);

        game.task(new Callable<Boolean>() {
            public Boolean call() throws Exception {

                game.hasMined = true;
                int mineResult = 1 + K.random.nextInt(6);
                switch (mineResult) {
                    case 1:
                    case 2:
                        game.ui.bottomBar.writeLine("[ORANGE]" +name + " [RED]encounters a Monster!");
                        game.mystery.play();
                        Thread.sleep(4000);
                        if (K.random.nextInt(3) == 0) {
                            game.ui.bottomBar.writeLine("[ORANGE]" +name + " [RED]dies. :-(");
                            game.die.play();
                            destroy();
                        } else {
                            game.ui.bottomBar.writeLine("[ORANGE]" +name + " [GREEN]survives!");

                        }
                        break;
                    case 5:
                        game.mine.play();
                        Thread.sleep(2000);
                        say("rok gott!");
                        game.stone += 17 + K.random.nextInt(19);
                        game.rocks.play();
                        Thread.sleep(2000);
                        break;
                    case 6:
                        game.mine.play();
                        Thread.sleep(2000);
                        say("guld gott!");
                        game.goldSound.play();
                        game.gold += 4 + K.random.nextInt(5);
                        Thread.sleep(2000);
                        break;
                    default:
                        game.mine.play();
                        Thread.sleep(4000);
                }
                getAppearance().setVisible(true);
                return true;
            }});

    }
}
