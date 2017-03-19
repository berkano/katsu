package mini73;

import katsu.KLauncher;
import katsu.KSettings;
import katsu.KUI;
import ld37wu.LD37wuGame;
import ld37wu.LD37wuSettings;
import ld37wu.LD37wuUI;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini73Runner extends KLauncher {

    public static void main(String[] args) {

        KSettings settings = new Mini73Settings();
        KUI ui = new Mini73UI();
        launch(new Mini73Game(), settings, ui);
    }

}
