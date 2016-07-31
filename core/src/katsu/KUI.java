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

    // Text display
    @Getter @Setter private ArrayList<KTextLine> text = new ArrayList<KTextLine>();
    @Getter @Setter private String helpText = "No help text provided";
    @Getter @Setter private String topText = "";
    @Getter @Setter private String secondaryText = "";
    @Getter @Setter private boolean showingHelp = false;
    @Getter @Setter private int leftMargin = 768;
    @Getter @Setter private int topMargin = 0;
    @Getter @Setter private BitmapFont font;
    @Getter @Setter private int fontHeight = 18;
    @Getter @Setter private int fontWidth = 18;
    @Getter @Setter private int lineDisplay = 10;
    @Getter @Setter private int lineCount = 0;

    // Rendering
    @Getter @Setter private SpriteBatch activeSpriteBatch;
    @Getter @Setter private ShapeRenderer activeShapeRenderer;
    @Getter @Setter private SpriteBatch uiSpriteBatch;
    @Getter @Setter private ShapeRenderer uiShapeRenderer;

    // Camera
    @Getter @Setter private Camera uiCamera;
    @Getter @Setter private Camera mainCamera;

    public void writeText(String s) {
        if (text.size() >= lineDisplay) {
            text.remove(0);
        }
        text.add(new KTextLine(s));
    }

    public void clearText() {
        text.clear();
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

    public void startRender() {
        getMainCamera().update();
        getActiveSpriteBatch().setProjectionMatrix(getMainCamera().combined);
        getActiveShapeRenderer().setProjectionMatrix(getMainCamera().combined);
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getActiveSpriteBatch().begin();
    }

    public void endRender() {
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
            float width = K.settings.getHres() - fontHeight * 2; // Keep a right border
            float height = K.settings.getVres()/2 + 224 - fontHeight * 2; // Based on number of lines to display
            K.ui.getUiShapeRenderer().rect(fontWidth, fontHeight, width, height);
        }
    }

    private void renderBitmaps() {
        renderHelpText();
        boolean gameIsPaused = K.runner.gamePaused();
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
        leftMargin = fontWidth;
        topMargin = 384 - fontHeight * lineDisplay - 4 - 16;
        for (KTextLine tl : text) {
            Color c = Color.WHITE;
            writeln(tl.text, c);
        }
        renderTopText();
    }

    private void renderTopText() {
        SpriteBatch batch = K.ui.getUiSpriteBatch();
        int yOffset = K.settings.getWindowHeight();
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

    public void writeln(String s, Color c) {
        if (s.startsWith("@")) {
            String[] bits = s.split(" ");
            String colour = bits[0];
            colour = colour.replaceAll("\\@", "");
            c = Colors.get(colour);
            s = s.replaceFirst("@"+colour+" ", "");
        }
        String wrappedStr = K.utils.wrap(s, 100);
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

    public void init() {

        // Font
        font = K.resource.loadBitmapFont("fonts/font.fnt", "fonts/font.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);

        // Game window
        Gdx.graphics.setTitle(K.settings.getGameName() + " :: " + K.settings.getGameAuthor() + " :: " + K.settings.getGameDescription());

        // Batch + renderer setup
        setActiveSpriteBatch(new SpriteBatch());
        setActiveShapeRenderer(new ShapeRenderer());
        setUiSpriteBatch(new SpriteBatch());
        setUiShapeRenderer(new ShapeRenderer());

        // Camera setup
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
