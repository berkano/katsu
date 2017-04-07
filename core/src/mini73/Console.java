package mini73;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.KInputProcessor;

/**
 * Created by shaun on 29/03/2017.
 */
public class Console extends KInputProcessor {

    Stage stage;
    Label label;
    Label.LabelStyle textStyle;
    BitmapFont font;
    TextBuffer textBuffer = new TextBuffer();
    private boolean visible = true;

    boolean firstUpdateDone = false;

    private int toggleKey = -1;

    public int getToggleKey() { return this.toggleKey; };

    public void clear() {
        textBuffer.clear();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == getToggleKey()) {
            setVisible(!isVisible());
        }
        return false; // Allow other components to respond to the key
    }

    public Console() {
        K.input.getMultiplexer().addProcessor(this);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void writeLine(String line, long decayMillis) {

        if (!firstUpdateDone) {
            firstUpdate();
        }

        textBuffer.writeLine(line, decayMillis);
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

        stage.act(Gdx.graphics.getDeltaTime());

        if (visible) {
            stage.draw();
        }

    }

    public void setBounds(float x, float y, float width, float height) {
        if (!firstUpdateDone) {
            firstUpdate();
        }
        label.setBounds(x, y, width, height);
    }

    public void setAlignment(int alignment) {
        label.setAlignment(alignment);
    }

    private void firstUpdate() {

        firstUpdateDone = true;

        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);

        font = K.graphics.font;
        textStyle = new Label.LabelStyle();
        textStyle.font = font;

        label = new Label("", textStyle);

        setBounds(0, 768 - 16,1024,-768);
        setAlignment(Align.bottomLeft);
        label.setFontScale(1f,-1f);

        stage.addActor(label);

    }


    public void dispose() {
        stage.dispose();
    }

    public boolean isVisible() {
        return visible;
    }

    public void writeLine(String s) {
        writeLine(s, 1000000000);
    }

    public Console setToggleKey(int toggleKey) {
        this.toggleKey = toggleKey;
        return this;
    }
}
