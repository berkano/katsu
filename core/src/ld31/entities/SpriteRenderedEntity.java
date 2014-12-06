package ld31.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import panko.Panko;
import panko.PankoEntityBase;
import panko.PankoResource;
import panko.PankoRoom;

/**
 * Created by shaun on 06/12/2014.
 */
public abstract class SpriteRenderedEntity extends PankoEntityBase {

    public SpriteRenderedEntity() {
        super();
        setSprite(new Sprite(PankoResource.loadTexture(getTextureResourceName())));
    }

    @Override
    public void render() {
        getSprite().setX(getX());
        getSprite().setY(getY());
        getSprite().setScale((float)(getRadius()/1000d));
        getSprite().setRotation((float)(getRotation()));
        getSprite().draw(Panko.getActiveSpriteBatch());
    }

    public abstract String getTextureResourceName();

}
