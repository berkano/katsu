package mini73;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Align;
import ext.ozmod.ChipPlayer;
import ext.ozmod.OZMod;
import katsu.*;
import mini73.rooms.MainRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini73Game extends KGame {

    public Console console = new Console();
    Console helpText = new Console();
    public Console statusBar = new Console();
    boolean printedLine = false;
    KMusic mus_observer = new KMusic();

    OZMod ozm;
    ChipPlayer player;
    int frequency;

    static Mini73Game _instance;

    Logger logger = LoggerFactory.getLogger(Mini73Game.class);

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.M) {
            if (mus_observer.getMusic().isPlaying()) {
                console.writeLine("Music paused.");
                mus_observer.getMusic().pause();
            } else {
                mus_observer.getMusic().play();
            }
        }

        return super.keyDown(keycode);
    }

    public void playMod(String file, float volume) {

        FileHandle module = K.resource.loadFile(file);
        logger.info("OzMod", "Play: " + module.path());
        player = ozm.getPlayer(module);
        // frequency = 44100;
        // frequency = 48000;
        frequency = 96000;
        player.setFrequency(frequency);
        player.setVolume(volume);
        player.setDaemon(true);
        player.setLoopable(false);
        player.play();
    }

    @Override
    public void start() {
        super.start();
        mus_observer.load("observer_v5.mp3");
        mus_observer.setAuthor("berkano");
        mus_observer.setTitle("observer");
        if (K.settings.isProduction()) {
            playLater(mus_observer, 5000);
        }

//        ozm = new OZMod();
//        ozm.initOutput();
//        playMod("music/singleton-mod-test.xm", 1);

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

        helpText.setAutoResize(false);
        helpText.setShaded(true);
        helpText.writeLine(K.resource.loadText("help.txt"));
        helpText.setAlignment(Align.center);
        helpText.setToggleKey(Input.Keys.H);


        //statusBar.writeLine("[CYAN]Status bar goes here...");
        statusBar.setAutoResize(false);
        statusBar.setBounds(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 2 * K.graphics.font.getLineHeight());
        statusBar.setAlignment(Align.topLeft);
        logger.info("GDX.height: " + Gdx.graphics.getHeight());

        _instance = this;

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



        statusBar.render();

//        logger.info("X: " + statusBar.label.getX());
//        logger.info("Y: " + statusBar.label.getY());
//        logger.info("W: " + statusBar.label.getWidth());
//        logger.info("H: " + statusBar.label.getHeight());
//
//        exit();

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
        return K.resource.scanTiledEntityClasses("mini73");
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

    public static Mini73Game instance() {
        if (_instance == null) {
            throw new RuntimeException("Game instance accessed before creation");
        }
        return _instance;
    }
}
