package katsu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.opengl.Display;

public class KDesktopLauncher {

    public static void launch(KGame implementation, KSettings settings) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.width = settings.getHres();
        config.height = settings.getVres();

        if (settings.isFullScreenBorderless()) {
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
            config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
        }

        config.foregroundFPS = settings.getTargetFrameRate();
        config.backgroundFPS = settings.getTargetFrameRate();
        config.vSyncEnabled = settings.isVsync();
        config.x = 0;
        config.y = 0;

        K.setImplementation(implementation);
        K.setSettings(settings);
        K.setWindowWidth(config.width);
        K.setWindowHeight(config.height);

        new LwjglApplication(new KGameRunner(), config);
    }
}
