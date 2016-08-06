package katsu;

import com.badlogic.gdx.graphics.FPSLogger;

/**
 * Created by shaun on 16/11/2014.
 */
public class KLogger {

    private FPSLogger fpsLogger = new FPSLogger();

    public void debug(String message) {
        console("DEBUG: "+message);
    }

    public void console(String message) {
        if (K.settings.isLogging()) {
            System.out.println(message);
        }
    }

    public void fatal(String message) {
        console("FATAL: "+message);
    }

    public void trace(String message) {
        console("TRACE: "+message);
    }

    public void pathfinder(KEntity kEntity, String s) {

        if (K.settings.isLogPathfinding()) {

            if (kEntity == null) {
                trace("pathfinder: " + s);
            } else {
                trace("pathfinder: " + kEntity.getClass().getSimpleName() + ": " + s);
            }
        }
    }

    public void warn(String message) {
        console("WARN: " + message);
    }

    public void fps() {
        if (K.settings.isLogFPS()) {
            fpsLogger.log();
        }
    }

}
