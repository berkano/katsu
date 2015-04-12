package net.berkanoid.panko.desktop.ld32;

import ld32.LD32Game;
import net.berkanoid.panko.desktop.PankoDesktopLauncher;
import panko.PankoSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD32Runner extends PankoDesktopLauncher {

    public static void main(String[] args) {
        PankoSettings settings = new PankoSettings();
        launch(new LD32Game(), settings);
    }

}
