package katsu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public abstract class GameDesktop {
	public static void main(String[] args) {
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
