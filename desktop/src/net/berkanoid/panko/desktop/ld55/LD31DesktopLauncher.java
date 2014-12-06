package net.berkanoid.panko.desktop.ld55;

import ld31.LD31PankoGame;
import ld31.LD31PankoSettings;
import net.berkanoid.panko.desktop.PankoDesktopLauncher;
import panko.PankoSettings;
import pankosample.PankoSampleGame;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD31DesktopLauncher extends PankoDesktopLauncher {

    public static void main(String[] args) {
        PankoSettings settings = new LD31PankoSettings();
        launch(new LD31PankoGame(), settings);
    }

}
