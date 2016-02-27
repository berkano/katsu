package ld34;

import katsu.KLauncher;
import katsu.KSettings;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD34Runner extends KLauncher {

    public static void main(String[] args) {

        KSettings settings = new LD34Settings();
        launch(new LD34Game(), settings);
    }

}
