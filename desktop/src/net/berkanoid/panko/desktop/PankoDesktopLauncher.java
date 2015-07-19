package net.berkanoid.panko.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import katsu.K;
import katsu.KGame;
import katsu.KGameRunner;
import katsu.KSettings;

public class PankoDesktopLauncher {

        public static void launch(KGame implementation, KSettings settings) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = settings.getHres();
        config.height = settings.getVres();
        config.foregroundFPS = settings.getTargetFrameRate();
        config.backgroundFPS = settings.getTargetFrameRate();
        config.vSyncEnabled = settings.isVsync();
        config.x = 0;
        config.y = 0;

        K.setImplementation(implementation);
        K.setSettings(settings);

        new LwjglApplication(new KGameRunner(), config);
	}
}
