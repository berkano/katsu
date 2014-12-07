package panko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 13/04/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class PankoUI {

    static BitmapFont font;
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
        font = PankoResource.loadBitmapFont("fonts/minecraftia-16px.fnt", "fonts/minecraftia-16px.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);
    }

    public void renderShadowBox() {
        // shadow box behind text pane
        if (text.size() > 0) {
            Color shade = new Color(0, 0, 0, 0.5f);

            Panko.getActiveShapeRenderer().setColor(shade);

            float x = fontHeight/2; // Keep a left border
            float y = fontHeight + fontHeight / 4; // Keep a bottom border (text.size() - 1)* fontHeight; // Relative from bottom of screen and based on number of lines to display
            float width = Panko.getSettings().getHres()/2 - fontHeight; // Keep a right border
            float height = 1 + text.size() * fontHeight; // Based on number of lines to display

            Panko.getActiveShapeRenderer().rect(x, y, width, height);
        }
    }

    public void render() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Panko.getActiveShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        renderShapes();
        Panko.getActiveShapeRenderer().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        Panko.getActiveSpriteBatch().begin();
        renderBitmaps();
        Panko.getActiveSpriteBatch().end();

    }

    private void renderShapes() {
        renderShadowBox();
    }

    private void renderBitmaps() {

        if (font == null) {
            loadFont();
        }

        // Remove old lines
        Iterator itr = text.iterator();
        while (itr.hasNext()) {
            TextLine tl = (TextLine) itr.next();
            if ((tl.added) < System.currentTimeMillis() - 1000) {
                itr.remove();
            }
        }

        lineCount = lineDisplay - text.size();
        leftMargin = fontHeight;
        topMargin = 384 - fontHeight * lineDisplay - 4 - 16;


        for (TextLine tl : text) {

            Color c = Color.WHITE;
            writeln(tl.text, c);
        }

    }

    public static String wrap(String in, int len) {
        in = in.trim();
        if (in.length() < len) return in;
        if (in.substring(0, len).contains("\n"))
            return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
        int place = Math.max(Math.max(in.lastIndexOf(" ", len), in.lastIndexOf("\t", len)), in.lastIndexOf("-", len));
        return in.substring(0, place).trim() + "\n" + wrap(in.substring(place), len);
    }


    public void writeln(String s, Color c) {

        String wrappedStr = wrap(s, 100);
        String[] lines = wrappedStr.split("\n");

        SpriteBatch batch = Panko.getActiveSpriteBatch();

        for (int i = 0; i < lines.length; i++) {
            int stringX = leftMargin;
            int stringY = topMargin - fontHeight * (lineCount - 1);

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

}
