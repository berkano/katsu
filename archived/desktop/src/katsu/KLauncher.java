package katsu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import katsu.game.KGame;
import katsu.resources.KSettings;
import katsu.test.troll.TrollCastleGame;

public class KLauncher {

    public static void main(String[] args) throws Exception {
        launch(TrollCastleGame.class);
    }

    public static void launch(Class<? extends KGame> runnerClass) throws Exception {
        KGame runner = runnerClass.newInstance();
        KSettings settings = runner.buildSettings();

        if (settings == null) {
            throw new RuntimeException("An implementation of KSettings must be provided by the Game");
        }

        launch(runner, settings);

    }

    public static void launch(KGame runner, KSettings settings) {

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
        config.x = LwjglApplicationConfiguration.getDesktopDisplayMode().width / 2 - config.width / 2;
        config.y = LwjglApplicationConfiguration.getDesktopDisplayMode().height / 2 - config.height / 2;

        config.audioDeviceBufferSize = 16384; // mod playback

        K.game = runner;
        K.settings = settings;

        K.settings.setWindowWidth(config.width);
        K.settings.setWindowHeight(config.height);

        new LwjglApplication(runner, config);
    }
}
