package net.berkanoid.panko.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import panko.Panko;
import panko.PankoGame;
import panko.PankoGameRunner;
import panko.PankoSettings;

public class PankoDesktopLauncher {

        public static void launch(PankoGame implementation, PankoSettings settings) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = settings.getHres();
        config.height = settings.getVres();
        config.foregroundFPS = settings.getTargetFrameRate();
        config.backgroundFPS = settings.getTargetFrameRate();
        config.vSyncEnabled = settings.isVsync();
        config.x = 0;
        config.y = 0;

        Panko.setImplementation(implementation);
        new LwjglApplication(new PankoGameRunner(), config);
	}
}
