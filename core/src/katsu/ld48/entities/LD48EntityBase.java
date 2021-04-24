package katsu.ld48.entities;

import katsu.ld48.LD48Game;
import katsu.ld48.LD48Sounds;
import katsu.model.KEntity;

/**
 * Created by shaun on 22/04/2017.
 */
public class LD48EntityBase extends KEntity {

    LD48Game game;
    LD48Sounds sounds;

    LD48EntityBase() {
        super();
        game = LD48Game.instance();
        sounds = game.sounds;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void describe(String desc) {
        game.ui.bottomBar.writeLine("[GRAY]" + desc);
        //game.sounds.select1.play();
    }


}
