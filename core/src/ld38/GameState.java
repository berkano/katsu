package ld38;

import katsu.KEntity;
import ld38.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 26/04/2017.
 */
public class GameState {

    TrollCastleGame game;
    Map room;

    public GameState(TrollCastleGame game, Map room) {
        this.game = game;
        this.room = room;
    }

    public void update() {

        game.trolls = 0;
        game.wallsBuilt = 0;
        game.goldTowersBuilt = 0;
        game.towersBuilt = 0;
        List<KEntity> trolls = new ArrayList<>();
        List<KEntity> tubes = new ArrayList<>();
        List<KEntity> towers = new ArrayList<>();
        for (KEntity e: room.getEntities()) {
            if (e instanceof Troll) {
                Troll t = (Troll) e;
                trolls.add(t);
                game.trolls++;
            }
            if (e instanceof SwimTube) {
                tubes.add(e);
            }
            if (e instanceof BaseTower) {
                towers.add(e);
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
