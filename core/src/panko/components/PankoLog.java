package panko.components;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoLog {

    public static void debug(String message) {
        console("DEBUG: "+message);
    }

    public static void console(String message) {
        System.out.println(message);
    }

    public static void fatal(String message) {
        console("FATAL: "+message);
    }

    public static void trace(String message) {
        console("TRACE: "+message);
    }
}
