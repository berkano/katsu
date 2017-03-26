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
    public OrthographicCamera camera;
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
        //font.setScale(1, -1);
        font.getData().markupEnabled = true;
        font.getData().breakChars = new char[] {'-'};
//        font.getData()
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.update();
    }

}
