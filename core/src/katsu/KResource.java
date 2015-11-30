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

    private static HashMap<String, Texture> textureCache = new HashMap<String, Texture>();

    public static String relativeResource(String resourcePath) {
        String resourceRoot = K.getImplementation().getResourceRoot();
        return resourceRoot + "/" + resourcePath;
    }

    public static Sound loadSound(String soundName) {
        return Gdx.audio.newSound(getResource(relativeResource("sounds/"+soundName)));
    }

    public static Texture loadTexture(String textureName) {
        if (textureCache.get(textureName) != null) {
            return textureCache.get(textureName);
        }
        else {
            Texture result = new Texture(getResource(relativeResource("textures/" + textureName)));
            textureCache.put(textureName, result);
            return result;
        }
    }

    private static FileHandle getResource(String fname) {
        FileHandle result = Gdx.files.getFileHandle(fname, Files.FileType.Internal);
        if (result == null) {
            throw new RuntimeException("Could not load resource " + fname);
        }
        return result;
    }

    public static BitmapFont loadBitmapFont(String fntFile, String pngFile) {

        return new BitmapFont(Gdx.files.internal(relativeResource(fntFile)), Gdx.files.internal(relativeResource(pngFile)), true);

    }

    public static String loadText(String textName) {

        FileHandle textHandle = getResource(relativeResource("text/" + textName));
        return textHandle.readString("UTF-8");

    }




}
