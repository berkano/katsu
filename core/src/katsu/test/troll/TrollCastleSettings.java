package katsu.test.troll;

import katsu.resources.KSettings;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollCastleSettings extends KSettings {
    @Override
    public String getGameName() {
        return "Troll Castle";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameDescription() {
        return "Ludum Dare 38 - Small World";
    }
}
