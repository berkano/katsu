package ld37wu;

import katsu.KLauncher;
import katsu.KSettings;
import katsu.KUI;
import ld34.LD34Game;
import ld34.LD34Settings;
import ld34.LD34UI;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD37wuRunner extends KLauncher {

    public static void main(String[] args) {

        KSettings settings = new LD37wuSettings();
        KUI ui = new LD37wuUI();
        launch(new LD37wuGame(), settings, ui);
    }

}
