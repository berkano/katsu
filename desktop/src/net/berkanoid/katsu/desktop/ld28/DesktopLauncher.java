package net.berkanoid.katsu.desktop.ld28;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import katsu.Game;
import katsu.KatsuGame;
import katsu.Settings;
import ld28.KatsuGameImpl;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        KatsuGame impl = new KatsuGameImpl();

        config.width = Settings.hres;
        config.height = Settings.vres;
        config.foregroundFPS = Settings.targetFrameRate;
        config.backgroundFPS = Settings.targetFrameRate;
        config.vSyncEnabled = Settings.vsync;
        config.x = 0;
        config.y = 0;

        new LwjglApplication(new Game(impl), config);
	}
}
