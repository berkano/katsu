package ld38;

/**
 * Created by shaun on 22/04/2017.
 */
public class TrollNamer {

    private static TrollNamer _instance;

    public static TrollNamer instance() {
        if (_instance == null) {
            _instance = new TrollNamer();
        }
        return _instance;
    }

    public String nextName() {
        return "Bob";
    }
}
