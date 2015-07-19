package katsu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class KDesktopLauncher {

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
