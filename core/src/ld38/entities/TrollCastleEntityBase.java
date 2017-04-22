package ld38.entities;

import katsu.KEntity;
import ld38.TrollCastleGame;
import ld38.TrollNamer;

/**
 * Created by shaun on 22/04/2017.
 */
public class TrollCastleEntityBase extends KEntity {

    TrollCastleGame game;
    TrollNamer namer;

    TrollCastleEntityBase() {
        super();
        game = TrollCastleGame.instance();
        namer = TrollNamer.instance();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void describe(String desc) {
        game.ui.bottomBar.writeLine("[GRAY]" + desc);
        game.select1.play();
    }


}
