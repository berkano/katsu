package net.berkanoid.panko.desktop.ld31v2;

import ld31v2.WarGame;
import ld31v2.LD31V2PankoSettings;
import net.berkanoid.panko.desktop.PankoDesktopLauncher;
import panko.PankoSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD31V2DesktopLauncher extends PankoDesktopLauncher {

    public static void main(String[] args) {
        PankoSettings settings = new LD31V2PankoSettings();
        launch(new WarGame(), settings);
    }

}
