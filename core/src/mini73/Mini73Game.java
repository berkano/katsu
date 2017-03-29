package mini73;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.KRoom;
import katsu.KRunner;
import katsu.KSettings;
import ld37wu.LD37wuSounds;
import ld37wu.LD37wuWorld;
import ld37wu.entities.Baby;
import ld37wu.entities.Present;
import ld37wu.entities.Santa;
import ld37wu.entities.Tree;
import mini73.rooms.MainRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini73Game extends KRunner {

    Stage stage;
    Label label;
    Label.LabelStyle textStyle;
    BitmapFont font;

    boolean firstUpdateDone = false;

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void render() {

        if (!firstUpdateDone) {
            doFirstUpdate();
        }

        super.render();

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void doFirstUpdate() {

        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);

        font = K.graphics.font;
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        label = new Label("hello world\nyo... [GREEN]green!", textStyle);

        label.setBounds(0, 768,1024,-768);
        label.setFontScale(1f,-1f);
        label.setAlignment(Align.topLeft);

        stage.addActor(label);

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
        stage.dispose();
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
