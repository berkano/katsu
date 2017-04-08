package katsu;

import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class K {

    public static Random random = new Random();
    public static KResource resource = new KResource();
    public static KInput input = new KInput();
    public static KTextureCache textureCache = new KTextureCache();
    public static KGraphics graphics = new KGraphics();

    // Provided by implementation
    public static KGame game;
    public static KSettings settings;

}
