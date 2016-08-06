package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;

public class KUI {

    // Text display
    private ArrayList<KTextLine> text = new ArrayList<KTextLine>();
    private String helpText;

    private int leftMargin = 768;
    private int topMargin = 0;
    private BitmapFont font;
    private int fontHeight = 18;
    private int fontWidth = 18;
    private int lineDisplay = 10;
    private int lineCount = 0;

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

            K.graphics.uiShapeRenderer.setColor(shade);

            float x = fontHeight/2; // Keep a left border
            float y = fontHeight + fontHeight / 4; // Keep a bottom border (text.size() - 1)* fontHeight; // Relative from bottom of screen and based on number of lines to display
            float width = K.settings.getHres() - fontHeight; // Keep a right border
            float height = 1 + text.size() * fontHeight; // Based on number of lines to display

            K.graphics.uiShapeRenderer.rect(x, y, width, height);
        }
    }

    public void preGlobalRender() {
        getMainCamera().update();
        K.graphics.activeSpriteBatch.setProjectionMatrix(getMainCamera().combined);
        K.graphics.activeShapeRenderer.setProjectionMatrix(getMainCamera().combined);
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        K.graphics.activeSpriteBatch.begin();
    }

    public void postGlobalRender() {
        K.graphics.activeSpriteBatch.end();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        K.graphics.uiShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        renderHelpShadowBox();
        renderShadowBox();
        K.graphics.uiShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        K.graphics.uiSpriteBatch.begin();
        renderText();
        K.graphics.uiSpriteBatch.end();
    }

    private void renderHelpShadowBox() {
        if (K.text.helpShowing()) {
            Color shade = new Color(0, 0, 0, 0.75f);
            K.graphics.uiShapeRenderer.setColor(shade);
            float width = K.settings.getHres() - fontHeight * 2; // Keep a right border
            float height = K.settings.getVres()/2 + 224 - fontHeight * 2; // Based on number of lines to display
            K.graphics.uiShapeRenderer.rect(fontWidth, fontHeight, width, height);
        }
    }

    private void renderText() {
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
            formatAndRenderLine(tl.text, c);
        }
        K.text.render(font);
    }

    private void renderHelpText() {
        if (!K.text.helpShowing()) return;
        topMargin = K.settings.getVres() - fontHeight - 8;
        leftMargin = fontHeight * 2;
        for (String s : helpText.split("\n")) {
            formatAndRenderLine(s, Color.WHITE);
        }
    }

    // handles wrapping and colour codes
    public void formatAndRenderLine(String s, Color c) {

        if (s.startsWith("@")) {
            String[] bits = s.split(" ");
            String colour = bits[0];
            colour = colour.replaceAll("\\@", "");
            c = Colors.get(colour);
            s = s.replaceFirst("@"+colour+" ", "");
        }

        String wrappedStr = K.utils.wrap(s, 100);
        String[] lines = wrappedStr.split("\n");

        for (String line : lines) {
            renderLine(line, c);
        }
    }

    private void renderLine(String line, Color c) {
        int stringX = leftMargin;
        int stringY = topMargin - fontHeight * (lineCount - 1);
        font.setColor(Color.BLACK);
        font.draw(K.graphics.uiSpriteBatch, line, stringX + 2, stringY + 2);
        font.setColor(c);
        font.draw(K.graphics.uiSpriteBatch, line, stringX, stringY);
        lineCount++;
    }

    public void init() {

        helpText = K.resource.loadText("help.txt");

        // Font
        font = K.resource.loadBitmapFont("fonts/font.fnt", "fonts/font.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);

        // Game window
        Gdx.graphics.setTitle(K.settings.getGameName() + " :: " + K.settings.getGameAuthor() + " :: " + K.settings.getGameDescription());

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
