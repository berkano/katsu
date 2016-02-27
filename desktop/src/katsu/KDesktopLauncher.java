package katsu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lwjgl.opengl.Display;

public class KDesktopLauncher {

    public static void launch(KGameRunner runner, KSettings settings) {

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

        // Centre it!
        config.x = LwjglApplicationConfiguration.getDesktopDisplayMode().width/2 - config.width / 2;
        config.y = LwjglApplicationConfiguration.getDesktopDisplayMode().height/2 - config.height / 2;

        K.runner = runner;
        K.settings = settings;
        K.setWindowWidth(config.width);
        K.setWindowHeight(config.height);

        new LwjglApplication(new KGameRunner(), config);
    }
}
