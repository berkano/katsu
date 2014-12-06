package panko;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoResource {

    public static String relativeResource(String resourcePath) {
        String resourceRoot = Panko.getImplementation().getResourceRoot();
        return resourceRoot + "/" + resourcePath;
    }

    public static Sound loadSound(String soundName) {
        return Gdx.audio.newSound(getResource(relativeResource("sounds/"+soundName)));
    }

    public static Texture loadTexture(String textureName) {
        return new Texture(getResource(relativeResource("textures/"+textureName)));
    }

    private static FileHandle getResource(String fname) {
        FileHandle result = Gdx.files.getFileHandle(fname, Files.FileType.Internal);
        if (result == null) {
            throw new RuntimeException("Could not load resource " + fname);
        }
        return result;
    }



}
