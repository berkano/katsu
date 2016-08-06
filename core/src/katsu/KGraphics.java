package katsu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 06/08/2016.
 */
public class KGraphics {

    public SpriteBatch activeSpriteBatch;
    public ShapeRenderer activeShapeRenderer;
    public SpriteBatch uiSpriteBatch;
    public ShapeRenderer uiShapeRenderer;

    public void init() {
        activeSpriteBatch = new SpriteBatch();
        activeShapeRenderer = new ShapeRenderer();
        uiSpriteBatch = new SpriteBatch();
        uiShapeRenderer = new ShapeRenderer();
    }

}
