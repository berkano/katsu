package net.berkanoid.panko.desktop.sample;

import net.berkanoid.panko.desktop.PankoDesktopLauncher;
import panko.PankoSettings;
import pankosample.PankoSampleGame;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoSampleDesktopLauncher extends PankoDesktopLauncher {

    public static void main(String[] args) {
        PankoSettings settings = new PankoSettings();
        launch(new PankoSampleGame(), settings);
    }

}