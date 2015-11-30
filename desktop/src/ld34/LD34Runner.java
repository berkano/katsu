package ld34;

import katsu.KDesktopLauncher;
import katsu.KSettings;
import ld33.LD33Game;
import ld33.LD33Settings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD34Runner extends KDesktopLauncher {

    public static void main(String[] args) {

        KSettings settings = new LD33Settings();
        launch(new LD34Game(), settings);
    }

}
