package katsu.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.input.KInputProcessor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 29/03/2017.
 */
public class KConsole extends KInputProcessor {

    private Stage stage;
    private Label label;
    private BitmapFont font;
    private KTextBuffer textBuffer = new KTextBuffer();
    private boolean visible = true;
    private boolean firstUpdateDone = false;
    private int toggleKey = -1;

    @Getter @Setter
    private boolean autoResize = true;
    private boolean shaded = true;

    private int getToggleKey() { return this.toggleKey; }

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

    public KConsole() {
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

        if (autoResize) {
            resizeToLabelContents();
        }

        stage.act(Gdx.graphics.getDeltaTime());

        if (visible) {
            stage.draw();
        }

    }

    private void resizeToLabelContents() {
        label.setHeight(-font.getLineHeight() * (1 + textBuffer.getSize()));
        label.setX(font.getLineHeight());
        label.setWidth(Gdx.graphics.getWidth() - font.getLineHeight());
        label.setY(0 + (1+textBuffer.getSize()) * font.getLineHeight());
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

        font = K.graphics.font;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        label = new Label("", labelStyle);

        if (shaded) {
            Pixmap labelColor = new Pixmap(128, 128, Pixmap.Format.RGBA8888);
            labelColor.setColor(new Color(0f, 0f, 0f, 0.5f));
            labelColor.fill();

            label.getStyle().background = new Image(new Texture(labelColor)).getDrawable();
        }

        setBounds(0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        setAlignment(Align.bottomLeft);
        label.setFontScale(1f,1f);

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

    public KConsole setToggleKey(int toggleKey) {
        this.toggleKey = toggleKey;
        return this;
    }

    public void setShaded(boolean shaded) {
        this.shaded = shaded;
    }

    public boolean isShaded() {
        return shaded;
    }

    public void setLineLimit(int limit) {
        textBuffer.setLineLimit(limit);
    }

    public int size() {
        return textBuffer.getSize();
    }
}
