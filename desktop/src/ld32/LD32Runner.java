package ld32;

import ld32.LD32Game;
import ld32.LD32Settings;
import katsu.KDesktopLauncher;
import katsu.KSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD32Runner extends KDesktopLauncher {

    public static void main(String[] args) {
        KSettings settings = new LD32Settings();
        launch(new LD32Game(), settings);
    }

}
