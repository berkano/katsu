package mini73;

import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.KRoom;
import katsu.KRunner;
import katsu.KSettings;
import ld37wu.LD37wuSounds;
import mini73.rooms.MainRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini73Game extends KRunner {

    Console console = new Console();
    Console helpText = new Console();
    boolean printedLine = false;

    @Override
    public void create() {
        super.create();
        helpText.writeLine("singleton - mini ld 73");
        helpText.setAlignment(Align.center);
    }

    @Override
    public void render() {

        super.render();

        if (!printedLine) {
            console.writeLine("~singleton~ by [ORANGE]berkano[WHITE]", 1000);
            console.writeLine("the first line!", 2000);
            console.writeLine("the second line!", 3000);
            console.writeLine("some more text!", 4000);
            console.writeLine("wibble", 5000);
            console.writeLine("bobble [RED]bobble[WHITE]", 6000);
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
