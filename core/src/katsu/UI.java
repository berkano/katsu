package katsu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 13/04/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class UI {

    BitmapFont font;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    public int lineCount = 0;
    public int cx = 24;
    public int cy = 24;
    public int leftMargin = 768;
    public int topMargin = 0;
    public int fontHeight = 18;
    public int lineDisplay = 10;
    public boolean showHealth = true;
    public int healthBarSize = 2;

    public ArrayList<TextLine> text = new ArrayList<TextLine>();

    public void writeText(String s) {
        if (text.size() >= lineDisplay) {
            text.remove(0);
        }
        text.add(new TextLine(s));
    }

    public void loadFont() {
        font = Util.loadBitmapFont("fonts/font.fnt", "fonts/font.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);
    }

    public void renderShadowBox() {
        // shadow box behind text pane
        if (text.size() > 0) {
            Color shade = new Color(0, 0, 0, 0.5f);
            Katsu.game.uiShapeRenderer.setColor(shade);
            Katsu.game.uiShapeRenderer.rect(fontHeight / 2, Settings.getVres() - (1 + text.size()) * fontHeight - fontHeight / 2, Settings.getHres() - fontHeight, (1 + text.size()) * fontHeight);
        }
    }

    public void render(Graphics g, Application gc, SpriteBatch batch) {

        if (font == null) {
            loadFont();
        }

        // Remove old lines
        Iterator itr = text.iterator();
        while (itr.hasNext()) {
            TextLine tl = (TextLine) itr.next();
            if ((tl.added) < System.currentTimeMillis() - 30000) {
                itr.remove();
            }
        }

        lineCount = lineDisplay - text.size();
        leftMargin = fontHeight;
        topMargin = 768 - fontHeight * lineDisplay - 4 - 16;


        for (TextLine tl : text) {
            Color c = Color.WHITE;
            if (tl.text.contains(" died.")) {
                c = Color.RED;
                if (tl.text.contains("Zombie")) {
                    c = Color.GREEN;
                }
            }

            writeln(tl.text, c, g, batch);
        }


    }

    public void writeln(String s, Color c, Graphics g, SpriteBatch batch) {

        String wrappedStr = Util.wrap(s, 100);
        String[] lines = wrappedStr.split("\n");

        for (int i = 0; i < lines.length; i++) {
            int stringX = leftMargin;
            int stringY = topMargin + fontHeight * (lineCount - 1);

            font.setColor(Color.BLACK);
            font.draw(batch, lines[i], stringX + 2, stringY + 2);
            font.setColor(c);
            font.draw(batch, lines[i], stringX, stringY);

            lineCount++;
        }
    }

    public void drawString(String s, Color c, int x, int y, SpriteBatch batch) {

        if (font == null) {
            loadFont();
        }

        font.setColor(c);
        font.draw(batch, s, x, y);
    }

    public void renderSelectedEntity() {
        //To change body of created methods use File | Settings | File Templates.
    }
}
