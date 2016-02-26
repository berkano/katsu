package katsu;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class KResource {

    private HashMap<String, Texture> textureCache = new HashMap<String, Texture>();

    public String relativeResource(String resourcePath) {
        String resourceRoot = K.runner.getResourceRoot();
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

        return new BitmapFont(Gdx.files.internal(relativeResource(fntFile)), Gdx.files.internal(relativeResource(pngFile)), true);

    }

    public String loadText(String textName) {

        FileHandle textHandle = getResource(relativeResource("text/" + textName));
        return textHandle.readString("UTF-8");

    }




}
