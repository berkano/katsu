package ld33;

import katsu.KLauncher;
import katsu.KSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD33Runner extends KLauncher {

    public static void main(String[] args) {

        KSettings settings = new LD33Settings();
        launch(new LD33Game(), settings);
    }

}
