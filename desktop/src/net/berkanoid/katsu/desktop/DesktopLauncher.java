package net.berkanoid.katsu.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import katsu.Game;
import katsu.Settings;
import net.berkanoid.katsu.KatsuGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = Settings.hres;
        config.height = Settings.vres;
        config.foregroundFPS = Settings.targetFrameRate;
        config.backgroundFPS = Settings.targetFrameRate;
        config.vSyncEnabled = Settings.vsync;
        config.x = 0;
        config.y = 0;

        new LwjglApplication(new Game(), config);
	}
}
