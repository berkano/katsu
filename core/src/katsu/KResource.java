package katsu;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by shaun on 16/11/2014.
 */
public class KResource {

    private HashMap<String, Texture> textureCache = new HashMap<String, Texture>();

    public String relativeResource(String resourcePath) {
        String resourceRoot = K.game.getResourceRoot();
        if (resourceRoot == null) {
            throw new RuntimeException("Game did not provide a resource root");
        }
        return resourceRoot + "/" + resourcePath;
    }

    public Sound loadSound(String soundName) {
        return Gdx.audio.newSound(getResource(relativeResource("sounds/"+soundName)));
    }

    public Texture loadTexture(String textureName) {
        if (textureCache.get(textureName) != null) {
            return textureCache.get(textureName);
        }
        else {
            Texture result = new Texture(getResource(relativeResource("textures/" + textureName)));
            textureCache.put(textureName, result);
            return result;
        }
    }

    private FileHandle getResource(String fname) {
        FileHandle result = Gdx.files.getFileHandle(fname, Files.FileType.Internal);
        if (result == null) {
            throw new RuntimeException("Could not load resource " + fname);
        }
        return result;
    }

    public BitmapFont loadBitmapFont(String fntFile, String pngFile) {
        return new BitmapFont(Gdx.files.internal(relativeResource(fntFile)), Gdx.files.internal(relativeResource(pngFile)), false);
    }

    public String loadText(String textName) {
        FileHandle textHandle = getResource(relativeResource("text/" + textName));
        return textHandle.readString("UTF-8");
    }

    public Music loadMusic(String name) {
        Music m = Gdx.audio.newMusic(getResource(relativeResource("music/" + name)));
        return m;
    }

    public FileHandle loadFile(String file) {
        return getResource(relativeResource(file));
    }

    public List<Class> scanTiledEntityClasses(String pkg) {
        Reflections reflections = new Reflections(pkg);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(KTiledMapEntity.class);
        return new ArrayList<Class>(annotated);
    }

}
