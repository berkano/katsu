package katsu;

import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class K {

    public static Random random = new Random();
    public static KLogger logger = new KLogger();
    public static KResource resource = new KResource();
    public static KUtils utils = new KUtils();
    public static KInput input = new KInput();

    // Provided by implementation
    public static KRunner runner;
    public static KSettings settings;
    public static KUI ui;

}
