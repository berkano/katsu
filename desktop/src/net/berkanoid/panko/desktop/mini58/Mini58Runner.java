package net.berkanoid.panko.desktop.mini58;

import ld31v2.LD31V2PankoSettings;
import ld31v2.WarGame;
import mini58.Mini58Game;
import net.berkanoid.panko.desktop.PankoDesktopLauncher;
import panko.PankoSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini58Runner extends PankoDesktopLauncher {

    public static void main(String[] args) {
        PankoSettings settings = new PankoSettings();
        launch(new Mini58Game(), settings);
    }

}
