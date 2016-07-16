package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KUI {

    BitmapFont font;

    public int lineCount = 0;
    public int leftMargin = 768;
    public int topMargin = 0;
    public int fontHeight = 18;
    public int lineDisplay = 10;
    public ArrayList<KTextLine> text = new ArrayList<KTextLine>();

    @Getter @Setter private boolean showingHelp = false;
    @Getter @Setter private String helpText = "No help text provided";
    @Getter @Setter private String topText = "";
    @Getter @Setter private String secondaryText = "";
    @Getter @Setter private SpriteBatch activeSpriteBatch;
    @Getter @Setter private ShapeRenderer activeShapeRenderer;
    @Getter @Setter private SpriteBatch uiSpriteBatch;
    @Getter @Setter private int windowWidth;
    @Getter @Setter private int windowHeight;
    @Getter @Setter private ShapeRenderer uiShapeRenderer;
    @Getter @Setter private Camera uiCamera;
    @Getter @Setter private Camera mainCamera;
    @Getter @Setter private HashMap<Class, TextureRegion> textureCache = new HashMap<Class, TextureRegion>();

    public TextureRegion tileStitch(int x, int y, TiledMapTileLayer tileLayer) {
        TextureRegion result = tileLayer.getCell(x, y).getTile().getTextureRegion();
        return result;
    }

    public void writeText(String s) {
        if (text.size() >= lineDisplay) {
            text.remove(0);
        }
        text.add(new KTextLine(s));
    }

    public void clearText() {
        text.clear();
    }

    public void loadFont() {
        font = K.resource.loadBitmapFont("fonts/font.fnt", "fonts/font.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);
    }

    public void renderShadowBox() {
        // shadow box behind text pane
        if (text.size() > 0) {
            Color shade = new Color(0, 0, 0, 0.33f);

            K.ui.getUiShapeRenderer().setColor(shade);

            float x = fontHeight/2; // Keep a left border
            float y = fontHeight + fontHeight / 4; // Keep a bottom border (text.size() - 1)* fontHeight; // Relative from bottom of screen and based on number of lines to display
            float width = K.settings.getHres() - fontHeight; // Keep a right border
            float height = 1 + text.size() * fontHeight; // Based on number of lines to display

            K.ui.getUiShapeRenderer().rect(x, y, width, height);
        }
    }

    public void render(ArrayList<KRoom> rooms) {

        getMainCamera().update();
        getActiveSpriteBatch().setProjectionMatrix(getMainCamera().combined);
        getActiveShapeRenderer().setProjectionMatrix(getMainCamera().combined);

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        getActiveSpriteBatch().begin();

        for (KRoom room : rooms) {
            if (room.isActive()) {
                room.render();
            }
        }

        getActiveSpriteBatch().end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        getUiShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        renderShapes();
        getUiShapeRenderer().end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        getUiSpriteBatch().begin();
        renderBitmaps();
        getUiSpriteBatch().end();

    }

    private void renderShapes() {
        renderHelpShadowBox();
        renderShadowBox();
    }

    private void renderHelpShadowBox() {
        if (isShowingHelp()) {
            
            Color shade = new Color(0, 0, 0, 0.75f);

            K.ui.getUiShapeRenderer().setColor(shade);

            float x = fontHeight; // Keep a left border
            float y = fontHeight; // Keep a bottom border (text.size() - 1)* fontHeight; // Relative from bottom of screen and based on number of lines to display
            float width = K.settings.getHres() - fontHeight * 2; // Keep a right border
            float height = K.settings.getVres()/2 + 224 - fontHeight * 2; // Based on number of lines to display

            K.ui.getUiShapeRenderer().rect(x, y, width, height);

        }
    }

    private void renderBitmaps() {

        renderHelpText();

        boolean gameIsPaused = K.runner.gamePaused();

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

        SpriteBatch batch = K.ui.getUiSpriteBatch();

        int yOffset = getWindowHeight();

        font.setColor(Color.BLACK);
        font.draw(batch, getTopText(), 4, yOffset - 4);
        font.setColor(Color.WHITE);
        font.draw(batch, getTopText(), 6, yOffset - 6);

        font.setColor(Color.BLACK);
        font.draw(batch, getSecondaryText(), 4, yOffset - 4 - 16);
        font.setColor(Color.PINK);
        font.draw(batch, getSecondaryText(), 6, yOffset - 6 - 16);

    }

    private void renderHelpText() {

        if (!isShowingHelp()) return;

        topMargin = K.settings.getVres() - fontHeight - 8;
        leftMargin = fontHeight * 2;

        for (String s : helpText.split("\n")) {
            writeln(s, Color.WHITE);
        }
    }

    public String wrap(String in, int len) {
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

        SpriteBatch batch = K.ui.getUiSpriteBatch();

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

    public void toggleFullScreenMode() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), false);
        } else {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), true);
        }
    }

    public void initalise() {

        Gdx.graphics.setTitle(K.settings.getGameName() + " :: " + K.settings.getGameAuthor() + " :: " + K.settings.getGameDescription());

        setActiveSpriteBatch(new SpriteBatch());
        setActiveShapeRenderer(new ShapeRenderer());

        setUiSpriteBatch(new SpriteBatch());
        setUiShapeRenderer(new ShapeRenderer());

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 1024;

        setMainCamera(new OrthographicCamera(viewportSize, viewportSize * (h / w)));
        getMainCamera().position.set(512, 768 / 2, 0);
        getMainCamera().update();

        setUiCamera(new OrthographicCamera(viewportSize, viewportSize * (h / w)));
        getUiCamera().position.set(512, 768 / 2, 0);
        getUiCamera().update();

    }
}
