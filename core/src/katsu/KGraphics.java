package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 06/08/2016.
 */
public class KGraphics {

    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    public SpriteBatch uiSpriteBatch;
    public ShapeRenderer uiShapeRenderer;
    public Camera uiCamera;
    public Camera mainCamera;

    public void init() {

        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        uiSpriteBatch = new SpriteBatch();
        uiShapeRenderer = new ShapeRenderer();

        setupCamera();

    }

    private void setupCamera() {

        // Camera setup
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float viewportSize = 1024;
        mainCamera = new OrthographicCamera(viewportSize, viewportSize * (h / w));
        mainCamera.position.set(512, 768 / 2, 0);
        mainCamera.update();
        mainCamera = new OrthographicCamera(viewportSize, viewportSize * (h / w));
        mainCamera.position.set(512, 768 / 2, 0);
        mainCamera.update();

    }

    public void preGlobalRender() {
        mainCamera.update();
        spriteBatch.setProjectionMatrix(mainCamera.combined);
        shapeRenderer.setProjectionMatrix(mainCamera.combined);
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
    }


}
