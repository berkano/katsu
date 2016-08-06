package katsu;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
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
    public static KText text = new KText();
    public static KTextureCache textureCache = new KTextureCache();
    public static KGraphics graphics = new KGraphics();

    // Provided by implementation
    public static KRunner runner;
    public static KSettings settings;
    public static KUI ui;

}
