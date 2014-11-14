package net.berkanoid.katsu.desktop.starter;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import katsu.Game;
import katsu.KatsuGame;
import katsu.Settings;
import starter.KatsuGameImpl;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        KatsuGame impl = new KatsuGameImpl();

        config.width = Settings.getHres();
        config.height = Settings.getVres();
        config.foregroundFPS = Settings.getTargetFrameRate();
        config.backgroundFPS = Settings.getTargetFrameRate();
        config.vSyncEnabled = Settings.isVsync();
        config.x = 0;
        config.y = 0;

        new LwjglApplication(new Game(impl), config);
	}
}
