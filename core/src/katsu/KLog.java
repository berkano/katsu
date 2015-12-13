package katsu;

/**
 * Created by shaun on 16/11/2014.
 */
public class KLog {

    public static void debug(String message) {
        console("DEBUG: "+message);
    }

    public static void console(String message) {
        if (K.getSettings().isLogging()) {
            System.out.println(message);
        }
    }

    public static void fatal(String message) {
        console("FATAL: "+message);
    }

    public static void trace(String message) {
        console("TRACE: "+message);
    }

    public static void pathfinder(KEntity kEntity, String s) {

        if (K.getSettings().isLogPathfinding()) {

            if (kEntity == null) {
                trace("pathfinder: " + s);
            } else {
                trace("pathfinder: " + kEntity.getClass().getSimpleName() + ": " + s);
            }
        }
    }

    public static void warn(String message) {
        console("WARN: " + message);
    }
}
