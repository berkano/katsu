package katsu;


import com.badlogic.gdx.audio.Music;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 02/04/2017.
 */
public class KMusic {

    @Getter @Setter
    private Music music;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String author;

    public void load(String filename) {
        music = K.resource.loadMusic(filename);
    }

    public void nowPlaying(KConsole console) {
        console.writeLine("[GRAY]now playing: [WHITE]" + getTitle() + " [GRAY]by [WHITE]" + getAuthor() + "[GRAY].", 10000);
    }
}
