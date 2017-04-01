package mini73;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.KRoom;
import katsu.KRunner;
import katsu.KSettings;
import ld37wu.LD37wuSounds;
import mini73.entities.mobs.PlayerPerson;
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
    Music music;

    @Override
    public void beforeFirstUpdate() {
        music = K.resource.loadMusic("observer_v5.mp3");
        music.play();
    }

    @Override
    public void create() {
        super.create();
        helpText.writeLine("\n\n\n\n\n\n\n\nsingleton");
        helpText.setAlignment(Align.center);
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
        LD37wuSounds.toggleMusic();
    }

    @Override
    public KSettings buildSettings() {
        return new Mini73Settings();
    }

}
