package ld31.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import panko.*;

/**
 * Created by shaun on 06/12/2014.
 */
public class Ship extends PankoEntityBase {

    private boolean isPlayer;

    public Ship() {
        super();
        setSprite(new Sprite(PankoResource.loadTexture("ship.png")));
    }

    @Override
    public void render() {
        getSprite().draw(Panko.getActiveSpriteBatch());
    }

    public static Ship newPlayerShip(PankoRoom room) {
        Ship result = new Ship();
        result.setPlayer(true);
        result.setPhysics(true);
        result.setWorld(room.getWorld());
        return result;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
