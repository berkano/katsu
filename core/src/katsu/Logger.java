package katsu;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 11/07/13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public class Logger {

    public static void info(String str) {
        if (Settings.isLogInfo()) {
            log("[INFO]: " + str);
        }
    }

    public static void debug(String str) {
        if (Settings.isLogDebug()) {
            log("[DEBUG]: " + str);
        }
    }

    public static void trace(String str) {
        if (Settings.isLogTrace()) {
            log("[TRACE]: " + str);
        }
    }

    public static void log(String str) {
        if (Settings.isLoggingEnabled()) {
            System.out.println(str);
        }
    }

    public static void error(String str) {
        if (Settings.isLogError()) {
            log("[ERROR]: " + str);
        }
    }
}
