package ld31v2;

import panko.PankoSettings;

/**
 * Created by shaun on 06/12/2014.
 */
public class LD31V2PankoSettings extends PankoSettings {

    @Override
    public String getGameDescription() {
        return "LD31";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameName() {
        return "lord wars";
    }

    @Override
    public int getTargetFrameRate() {
        return 20;
    }
}
