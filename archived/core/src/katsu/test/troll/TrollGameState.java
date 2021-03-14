package katsu.test.troll;

import katsu.model.KEntity;
import katsu.test.troll.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 26/04/2017.
 */
public class TrollGameState {

    private TrollCastleGame game;
    private TrollMap room;

    TrollGameState(TrollCastleGame game, TrollMap room) {
        this.game = game;
        this.room = room;
    }

    public void update() {

        game.trolls = 0;
        game.wallsBuilt = 0;
        game.goldTowersBuilt = 0;
        game.towersBuilt = 0;

        for (KEntity e: room.getEntities()) {
            if (e instanceof Troll) {
                game.trolls++;
            }
            if (e instanceof Wall) {
                game.wallsBuilt++;
            }
            if (e instanceof Tower) {
                game.towersBuilt++;
            }
            if (e instanceof GoldTower) {
                game.goldTowersBuilt++;
            }
        }

    }
}
