package katsu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 06/08/2016.
 */
public class KText {

    @Getter @Setter private String top = "";
    @Getter @Setter private String secondary = "";
    private boolean showingHelp = false;

    public void render() {
        BitmapFont font = K.graphics.font;
        int yOffset = K.settings.getWindowHeight();
        font.setColor(Color.BLACK);
        font.draw(K.graphics.uiSpriteBatch, K.text.getTop(), 4, yOffset - 4);
        font.setColor(Color.WHITE);
        font.draw(K.graphics.uiSpriteBatch, top, 6, yOffset - 6);
        font.setColor(Color.BLACK);
        font.draw(K.graphics.uiSpriteBatch, secondary, 4, yOffset - 4 - 16);
        font.setColor(Color.PINK);
        font.draw(K.graphics.uiSpriteBatch, secondary, 6, yOffset - 6 - 16);
    }

    public boolean helpShowing() {
        return showingHelp;
    }

    public void hideHelp() {
        showingHelp = false;
    }

    public void showHelp() {
        showingHelp = true;
    }
}
