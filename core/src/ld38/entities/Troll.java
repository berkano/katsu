package ld38.entities;

import com.badlogic.gdx.graphics.Color;
import katsu.K;
import katsu.KDirection;
import katsu.KEntity;
import katsu.KTiledMapEntity;
import ld38.DevHelper;
import ld38.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Troll extends TrollCastleEntityBase {

    public String name;

    Logger logger = LoggerFactory.getLogger(Troll.class);

    Map map;
    public boolean hadFish = false;
    public int hunger = 0;
    private SwimTube swimTube = null;

    private TrollMind mind = new TrollMind(this, game);

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
        mind.updatePsychedelics();
        moveSwimTube();
        doMovement();

    }

    private void doMovement() {

        int moveInterval = mind.isPsychedelic() ? 250 : 750;
        if (!lastMovedMoreThan(moveInterval)) return;

        if (getTargetEntity() != null) {

            KEntity target = getTargetEntity();
            if (mind.hasHadPsychedelics()) {
                map.makeWaterPassable();
            }

            KDirection suggestion = getGrid().doPathFinding(target.getGrid().getX(), target.getGrid().getY());

            if (suggestion != null) {
                doMovementInDirection(suggestion);
            }

            map.makeWaterSolid();

        }

    }

    private void doMovementInDirection(KDirection suggestion) {

        boolean result = tryMoveGridDirection(suggestion);
        if (result) {
            boolean onWater = false;
            List<KEntity> entityList = getRoom().findEntitiesAtPoint(getX() + 2, getY() + 2);
            for (KEntity e : entityList) {
                if (e instanceof Water) onWater = true;
            }
            if (!onWater) {
                game.walk.play();
            } else {
                game.water.play();
            }
            juiceRotation();
        }

    }

    private void moveSwimTube() {

        if (swimTube != null) {
            swimTube.setX(getX());
            swimTube.setY(getY());
            swimTube.getAppearance().setVisible(getAppearance().isVisible());
        }

    }

    @Override
    public void onClick() {
        super.onClick();
        say("hi ho");
    }

    public void say(String utterance) {

        int talkSound = K.random.nextInt(4);

        if (talkSound == 0) game.talk1.play();
        if (talkSound == 1) game.talk2.play();
        if (talkSound == 2) game.talk3.play();
        if (talkSound == 3) game.talk4.play();

        game.ui.bottomBar.writeLine("[ORANGE]"+ toString() + "[GRAY]: [GREEN]" + utterance+"[WHITE]");
    }

    public void mine() {

        hunger++;

        if (hunger > 3) {
            say("[RED]me too hungry. feed me fishies. [CYAN]><>");
            return;
        }

        getAppearance().setVisible(false);

        game.task(new Callable<Boolean>() {
            public Boolean call() throws Exception {

                int mineResult = 1 + K.random.nextInt(6);
                switch (mineResult) {
                    case 1:
                    case 2:
                        encounterMonster();
                        break;
                    case 3:
                        break;
                    case 4:
                    case 5:
                        gotStone();
                        break;
                    // 6: Gold!
                    case 6:
                        gotGold();
                        break;
                    default:
                        game.mine.play();
                        Thread.sleep(4000);
                }
                getAppearance().setVisible(true);
                return true;
            }});

    }

    private void gotGold() throws InterruptedException{
        game.mine.play();
        Thread.sleep(2000);
        say("got gold!");
        game.goldSound.play();
        game.gold += 4 + K.random.nextInt(5);
        Thread.sleep(2000);
    }

    private void gotStone() throws InterruptedException{
        game.mine.play();
        Thread.sleep(2000);
        say("got stone!");
        game.stone += 17 + K.random.nextInt(19);
        game.rocks.play();
        Thread.sleep(2000);
    }

    private void encounterMonster() throws InterruptedException{
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
    }

    public void addSwimTube() {
        if (swimTube == null) {
            swimTube = new SwimTube();
            swimTube.setX(getX());
            swimTube.setY(getY());
            getRoom().addNewEntity(swimTube);
        }
    }

    public TrollMind getMind() {
        return mind;
    }
}
