package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 13/04/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class KUI {

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

    public static HashMap<String, String> messageReplacements = new HashMap<String, String>();
    private String lastTextWritten = "";

    public static HashMap<String, String> getMessageReplacements() {
        return messageReplacements;
    }

    public ArrayList<KTextLine> text = new ArrayList<KTextLine>();
    private boolean showingHelp = false;
    private String helpText = "No help text provided";

    private String topText = "";
    private String secondaryText = "";

    public void writeText(String s) {

        if (s.equals(lastTextWritten) && !s.contains("Paused")) return;
        lastTextWritten = s;

        if (text.size() >= lineDisplay) {
            text.remove(0);
        }
        text.add(new KTextLine(s));
    }

    public void clearText() {
        text.clear();
    }

    public void loadFont() {
        font = KResource.loadBitmapFont("fonts/font.fnt", "fonts/font.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);
    }

    public void renderShadowBox() {
        // shadow box behind text pane
        if (text.size() > 0) {
            Color shade = new Color(0, 0, 0, 0.33f);

            K.getUiShapeRenderer().setColor(shade);

            float x = fontHeight/2; // Keep a left border
            float y = fontHeight + fontHeight / 4; // Keep a bottom border (text.size() - 1)* fontHeight; // Relative from bottom of screen and based on number of lines to display
            float width = K.getSettings().getHres() - fontHeight; // Keep a right border
            float height = 1 + text.size() * fontHeight; // Based on number of lines to display

            K.getUiShapeRenderer().rect(x, y, width, height);
        }
    }

    public void render() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        K.getUiShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        renderShapes();
        K.getUiShapeRenderer().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        K.getUiSpriteBatch().begin();
        renderBitmaps();
        K.getUiSpriteBatch().end();

    }

    private void renderShapes() {
        renderHelpShadowBox();
        renderShadowBox();
    }

    private void renderHelpShadowBox() {
        if (isShowingHelp()) {
            
            Color shade = new Color(0, 0, 0, 0.75f);

            K.getUiShapeRenderer().setColor(shade);

            float x = fontHeight; // Keep a left border
            float y = fontHeight; // Keep a bottom border (text.size() - 1)* fontHeight; // Relative from bottom of screen and based on number of lines to display
            float width = K.getSettings().getHres() - fontHeight * 2; // Keep a right border
            float height = K.getSettings().getVres()/2 + 224 - fontHeight * 2; // Based on number of lines to display

            K.getUiShapeRenderer().rect(x, y, width, height);

        }
    }

    private void renderBitmaps() {

        renderHelpText();

        boolean gameIsPaused = K.gamePaused();

        if (font == null) {
            loadFont();
        }

        // Remove old lines
        Iterator itr = text.iterator();
        while (itr.hasNext()) {
            KTextLine tl = (KTextLine) itr.next();
            if ((tl.added) < System.currentTimeMillis() - 15000) {
                if (!gameIsPaused) {
                    itr.remove();
                }
            }
        }

        lineCount = lineDisplay - text.size();
        leftMargin = fontHeight;
        topMargin = 384 - fontHeight * lineDisplay - 4 - 16;


        for (KTextLine tl : text) {

            Color c = Color.WHITE;
            writeln(tl.text, c);
        }

        renderTopText();

    }

    private void renderTopText() {

        SpriteBatch batch = K.getUiSpriteBatch();

        int yOffset = K.getWindowHeight();

        font.setColor(Color.BLACK);
        font.draw(batch, doReplacements(getTopText()), 4, yOffset - 4);
        font.setColor(Color.WHITE);
        font.draw(batch, doReplacements(getTopText()), 6, yOffset - 6);

        font.setColor(Color.BLACK);
        font.draw(batch, doReplacements(getSecondaryText()), 4, yOffset - 4 - 16);
        font.setColor(Color.PINK);
        font.draw(batch, doReplacements(getSecondaryText()), 6, yOffset - 6 - 16);

    }

    private String doReplacements(String originalText) {
        for (String r : messageReplacements.keySet()) {
            originalText = originalText.replace(r, messageReplacements.get(r));
        }
        // TODO - LD33 specific!!
        if (originalText.startsWith("You swing")) {
            originalText = originalText.replace("but misses", "but you miss");
        }
        return originalText;
    }

    private void renderHelpText() {

        if (!isShowingHelp()) return;

        topMargin = K.getSettings().getVres() - fontHeight - 8;
        leftMargin = fontHeight * 2;

        for (String s : helpText.split("\n")) {
            writeln(s, Color.WHITE);
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

        if (font == null) loadFont();

        if (s.startsWith("@")) {
            String[] bits = s.split(" ");
            String colour = bits[0];
            colour = colour.replaceAll("\\@", "");

            c = Colors.get(colour);
            s = s.replaceFirst("@"+colour+" ", "");
        }

        String wrappedStr = wrap(s, 100);
        String[] lines = wrappedStr.split("\n");

        SpriteBatch batch = K.getUiSpriteBatch();

        for (int i = 0; i < lines.length; i++) {
            int stringX = leftMargin;
            int stringY = topMargin - fontHeight * (lineCount - 1);

            font.setColor(Color.BLACK);
            font.draw(batch, doReplacements(lines[i]), stringX + 2, stringY + 2);
            font.setColor(c);
            font.draw(batch, doReplacements(lines[i]), stringX, stringY);

            lineCount++;
        }
    }

    public void drawString(String s, Color c, int x, int y, SpriteBatch batch) {

        if (font == null) {
            loadFont();
        }

        font.setColor(c);
        font.draw(batch, doReplacements(s), x, y);
    }

    public void modalDialog(String s) {
        writeText("@BLUE " + s);

    }

    public boolean isShowingHelp() {
        return showingHelp;
    }

    public void setShowingHelp(boolean showingHelp) {
        this.showingHelp = showingHelp;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }
}
