package ld31.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import panko.*;

/**
 * Created by shaun on 06/12/2014.
 */
public class Ship extends PankoEntityBase {

    private boolean isPlayer;
    private long lastMoveKeyMillis = System.currentTimeMillis();

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    public Ship() {
        super();
        Panko.getInputMultiplexer().addProcessor(this);
        setSprite(new Sprite(PankoResource.loadTexture("ship.png")));
    }

    @Override
    public void update() {
        super.update();
        moveShipBasedOnKeyPress();
    }

    private void moveShipBasedOnKeyPress() {

        if (System.currentTimeMillis() < lastMoveKeyMillis + 25) return;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            accelerate(1);
            lastMoveKeyMillis = System.currentTimeMillis();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            accelerate(-1);
            lastMoveKeyMillis = System.currentTimeMillis();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotate(3);
            lastMoveKeyMillis = System.currentTimeMillis();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotate(-3);
            lastMoveKeyMillis = System.currentTimeMillis();
        }
    }

    @Override
    public void render() {
        getSprite().setX(getX());
        getSprite().setY(getY());
        getSprite().setRotation(getRotation());
        getSprite().draw(Panko.getActiveSpriteBatch());
    }

    public static Ship newPlayerShip(PankoRoom room) {
        Ship result = new Ship();
        result.setPlayer(true);
        return result;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
