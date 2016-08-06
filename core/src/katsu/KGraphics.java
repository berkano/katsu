package katsu;

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

    public void init() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        uiSpriteBatch = new SpriteBatch();
        uiShapeRenderer = new ShapeRenderer();
    }

}
