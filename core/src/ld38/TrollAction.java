package ld38;

import katsu.KEntity;
import ld38.entities.*;

import java.util.concurrent.Callable;

/**
 * Created by shaun on 26/04/2017.
 */
public class TrollAction {

    private final TrollCastleGame game;
    private final TrollManager manager;
    private final Map room;

    public TrollAction(TrollCastleGame game, TrollManager trollManager, Map room) {
        this.game = game;
        this.manager = trollManager;
        this.room = room;
    }

    public void act(KEntity target) {

        Troll selectedTroll = manager.getSelectedTroll();

        if (target instanceof Mushroom) {
            selectedTroll.getMind().setPsychedelic(true);
            game.hasEatenMushroom = true;
            target.destroy();
            game.psych.play();
        }

        if (target instanceof Mine) {
            selectedTroll.mine();
        }

        if (target instanceof Mud) {
            BabyMushroom babyMushroom = new BabyMushroom();
            babyMushroom.setX(selectedTroll.getX());
            babyMushroom.setY(selectedTroll.getY());
            room.addNewEntity(babyMushroom);
            selectedTroll.say("mushroom planted!");
            game.plant.play();
        }

        if (target instanceof Fish) {
            selectedTroll.hadFish = true;
            selectedTroll.say("yum yum fish!");
            selectedTroll.hunger = 0;
            game.fish.play();
            target.destroy();
            final int x = target.getX();
            final int y = target.getY();
            game.task(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Thread.sleep(30000);
                    Fish fish = new Fish();
                    fish.setX(x);
                    fish.setY(y);
                    room.addNewEntity(fish);
                    return true;
                }
            });
        }


    }
}
