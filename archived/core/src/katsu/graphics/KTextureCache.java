package katsu.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Created by shaun on 06/08/2016.
 */
public class KTextureCache {

    private HashMap<Class, TextureRegion> cache = new HashMap<Class, TextureRegion>();

    public TextureRegion get(Class clazz) {
        return cache.get(clazz);
    }

    public void put(Class clazz, TextureRegion textureRegion) {
        cache.put(clazz, textureRegion);
    }
}
