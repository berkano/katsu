package mini73;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Align;
import katsu.*;
import mini73.rooms.MainRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini73Game extends KRunner {

    Console console = new Console();
    Console helpText = new Console().setToggleKey(Input.Keys.H);
    boolean printedLine = false;
    KMusic mus_observer = new KMusic();

    static Mini73Game _instance;

    @Override
    public boolean keyDown(int keycode) {
        if (mus_observer.getMusic().isPlaying()) {
            console.writeLine("Music paused.");
            mus_observer.getMusic().pause();
        } else {
            mus_observer.getMusic().play();
        }

        return super.keyDown(keycode);
    }

    @Override
    public void beforeFirstUpdate() {
        mus_observer.load("observer_v5.mp3");
        mus_observer.setAuthor("berkano");
        mus_observer.setTitle("observer");
        if (K.settings.isProduction()) {
            playLater(mus_observer, 5000);
        }
    }

    public void playLater(final KMusic music, final long delayMillis) {

        task(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Thread.sleep(delayMillis);
                music.getMusic().play();
                music.getMusic().setLooping(true);
                music.nowPlaying(console);
                return true;
            }});
    }



    @Override
    public void create() {
        super.create();
        helpText.writeLine("\n\n\n\n\n\n\n\nsingleton");
        helpText.setAlignment(Align.center);


        console.writeLine("welcome to ~singleton~, berkano's LD28 entry.");
        console.writeLine("major tim is lost in space. one prisoner, one");
        console.writeLine("sheep, one inventory slot, one goal:");
        console.writeLine("help him find his way back to earth");
        console.writeLine("press h for help.");


    }

    private void task(Callable<Boolean> c) {
        Executors.newSingleThreadExecutor().submit(
                new FutureTask<>(c));
    }

    private void writeLater(final String text, final long millis) {
        task(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Thread.sleep(millis);
                console.writeLine(text, 10000);
                return true;
            }});
    }

    @Override
    public void render() {

        super.render();

        if (!printedLine) {

//            writeLater("~singleton~ by [ORANGE]berkano[WHITE]", 1000);
//            writeLater("the first line!", 2000);
//            writeLater("the second line!", 3000);
//            writeLater("some more text!", 4000);
//            writeLater("wibble", 5000);
//            writeLater("bobble [RED]bobble[WHITE]", 6000);
            printedLine = true;
        }

        console.render();
        helpText.render();

    }

    private void renderGameText() {



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        console.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public ArrayList<KRoom> getRooms() {

        ArrayList<KRoom> rooms = new ArrayList<KRoom>();
        rooms.add(new MainRoom());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "mini73";
    }

    @Override
    public List<Class> getClassLookup() {
        return K.utils.scanTiledEntityClasses("mini73");
    }

    private void addClassesTo(HashMap<String, Class> classHashMap, Class... classes) {

        for (Class c: classes) {
            classHashMap.put(c.getSimpleName(), c);
        }

    }

    @Override
    public void toggleMusic() {
//        LD37wuSounds.toggleMusic();
    }

    @Override
    public KSettings buildSettings() {
        return new Mini73Settings();
    }

}
