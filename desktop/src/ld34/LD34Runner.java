package ld34;

import katsu.KDesktopLauncher;
import katsu.KSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD34Runner extends KDesktopLauncher {

    public static void main(String[] args) {

        KSettings settings = new LD34Settings();
        launch(new LD34Game(), settings);
    }

}
