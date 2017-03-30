package mini73;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import katsu.K;

/**
 * Created by shaun on 29/03/2017.
 */
public class Console {

    Stage stage;
    Label label;
    Label.LabelStyle textStyle;
    BitmapFont font;
    TextBuffer textBuffer = new TextBuffer();

    boolean firstUpdateDone = false;

    public void setDecayMillis(long newMillis) {
        textBuffer.setDecayMillis(newMillis);
    }

    public void writeLine(String line) {

        if (!firstUpdateDone) {
            firstUpdate();
        }

        textBuffer.writeLine(line);
        label.setText(textBuffer.toString());
    }

    public void render() {

        if (!firstUpdateDone) {
            firstUpdate();
        }

        textBuffer.refresh();
        if (textBuffer.updated) {
            label.setText(textBuffer.toString());
        }

        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


    private void firstUpdate() {

        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);

        font = K.graphics.font;
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        label = new Label("", textStyle);

        label.setBounds(0, 768,1024,-768);
        label.setFontScale(1f,-1f);
        label.setAlignment(Align.topLeft);

        stage.addActor(label);

        firstUpdateDone = true;

    }


    public void dispose() {
        stage.dispose();
    }
}
