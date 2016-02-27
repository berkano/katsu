package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class K {

    // Provided as static for convenience
    public static Random random = new Random();
    public static KLogger logger = new KLogger();
    public static KResource resource = new KResource();
    public static KUtils utils = new KUtils();
    public static KInput input = new KInput();

    // Provided by implementation
    public static KGameRunner runner;
    public static KSettings settings;
    public static KUI ui;

    // Everything else
    @Getter @Setter private static int windowWidth;
    @Getter @Setter private static int windowHeight;
    @Getter @Setter private static long lastRogueUpdate = System.currentTimeMillis();

}
