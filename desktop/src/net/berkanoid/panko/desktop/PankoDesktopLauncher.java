package net.berkanoid.panko.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import katsu.KatsuGame;
import katsu.Settings;
import panko.PankoGame;
import panko.PankoGameRunner;
import starter.KatsuGameImpl;

public class PankoDesktopLauncher {

        public static void launch(PankoGame implementation) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        KatsuGame impl = new KatsuGameImpl();

        config.width = Settings.getHres();
        config.height = Settings.getVres();
        config.foregroundFPS = Settings.getTargetFrameRate();
        config.backgroundFPS = Settings.getTargetFrameRate();
        config.vSyncEnabled = Settings.isVsync();
        config.x = 0;
        config.y = 0;

        new LwjglApplication(new PankoGameRunner(implementation), config);
	}
}
