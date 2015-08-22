package ld33;

import katsu.KDesktopLauncher;
import katsu.KSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD33Runner extends KDesktopLauncher {

    public static void main(String[] args) {

        KSettings settings = new LD33Settings();
        launch(new LD33Game(), settings);
    }

}
