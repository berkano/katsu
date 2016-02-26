package katsu;

/**
 * Created by shaun on 16/11/2014.
 */
public class KLogger {

    public void debug(String message) {
        console("DEBUG: "+message);
    }

    public void console(String message) {
        if (K.getSettings().isLogging()) {
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

        if (K.getSettings().isLogPathfinding()) {

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
}