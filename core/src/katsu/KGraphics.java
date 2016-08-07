package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by shaun on 06/08/2016.
 */
public class KGraphics {

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    public SpriteBatch uiSpriteBatch;
    public ShapeRenderer uiShapeRenderer;
    public Camera camera;
    public BitmapFont font;

    public void init() {

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        uiSpriteBatch = new SpriteBatch();
        uiShapeRenderer = new ShapeRenderer();

        setupCamera();
        setupFont();

    }

    private void setupFont() {

        font = K.resource.loadBitmapFont("fonts/font.fnt", "fonts/font.png");
        font.setColor(1f, 1f, 1f, 1f);
        font.setScale(1, -1);

    }

    private void setupCamera() {

        // Camera setup
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 1024;
        camera = new OrthographicCamera(viewportSize, viewportSize * (h / w));
        camera.position.set(512, 768 / 2, 0);
        camera.update();
        camera = new OrthographicCamera(viewportSize, viewportSize * (h / w));
        camera.position.set(512, 768 / 2, 0);
        camera.update();

    }

    public void preGlobalRender() {
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
    }


}
