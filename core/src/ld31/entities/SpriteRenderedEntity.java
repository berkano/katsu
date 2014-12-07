package ld31.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import panko.Panko;
import panko.PankoEntityBase;
import panko.PankoResource;

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


        double dxCam = getX() - Panko.getMainCamera().position.x;
        double dyCam = getY() - Panko.getMainCamera().position.y;
        double dCam = Math.sqrt(dxCam * dxCam + dyCam * dyCam);

        double distanceFromCameraScaleFactor = Math.sqrt(dCam * 1000d);
        if (distanceFromCameraScaleFactor < 1) distanceFromCameraScaleFactor = 1;
        double maxScaleFactor = 10; //getRadius()/250d;
        if (distanceFromCameraScaleFactor > maxScaleFactor) distanceFromCameraScaleFactor = maxScaleFactor;

//        getSprite().setScale((float)(getRadius()/(1000d*distanceFromCameraScaleFactor)));

        double apparentSize = getRadius() / (1000d * distanceFromCameraScaleFactor);

        double plotX = getX();
        double plotY = getY();
        double maxDist = Panko.getMainCamera().viewportHeight / 2;
        if (dCam > maxDist) {
            double farness = Math.pow(dCam / maxDist, 1.1d);

            plotX = Panko.getMainCamera().position.x + dxCam / farness;
            plotY = Panko.getMainCamera().position.y + dyCam / farness;
        }

        getSprite().setX((float)plotX);
        getSprite().setY((float) plotY);
        getSprite().setScale((float)apparentSize);
        getSprite().setRotation((float)(getRotation()));
        getSprite().draw(Panko.getActiveSpriteBatch());
    }

    public abstract String getTextureResourceName();

}
