package katsu.test.troll.entities;

import katsu.model.KEntity;
import katsu.test.troll.TrollCastleGame;
import katsu.test.troll.TrollCastleSounds;
import katsu.test.troll.TrollNamer;

/**
 * Created by shaun on 22/04/2017.
 */
public class TrollCastleEntityBase extends KEntity {

    TrollCastleGame game;
    TrollCastleSounds sounds;
    TrollNamer namer;

    TrollCastleEntityBase() {
        super();
        game = TrollCastleGame.instance();
        namer = TrollNamer.instance();
        sounds = game.sounds;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void describe(String desc) {
        game.ui.bottomBar.writeLine("[GRAY]" + desc);
        game.sounds.select1.play();
    }


}
