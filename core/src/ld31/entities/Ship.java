package ld31.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import ld31.LD31Sounds;
import panko.*;

/**
 * Created by shaun on 06/12/2014.
 */
public class Ship extends SpriteRenderedEntity {

    private boolean isPlayer;
    private long lastMoveKeyMillis = 0;
    private long lastAccelerateMillis = 0;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            fireBullet();
            return true;
        }
        return false;
    }

    private void fireBullet() {
        Bullet bullet = new Bullet();
        bullet.setX(getX());
        bullet.setY(getY());
        bullet.setRotation(getRotation());
        bullet.setVelocity(5 + getVelocity() * 3);
        bullet.setRadius(250);
        Panko.queueEntityToRoom(getRoom(), bullet);
        Panko.queueEntityToTop(getRoom(), this);
        LD31Sounds.bullet.play();
    }

    private void createEngineSmoke(int speedFactor) {
        Smoke smoke = new Smoke();
        smoke.setX(getX());
        smoke.setY(getY());
        smoke.setRotation(getRotation());
        smoke.setVelocity(-getVelocity() * 2 - speedFactor);
        smoke.setRadius(500);
        Panko.queueEntityToRoom(getRoom(), smoke);
        Panko.queueEntityToTop(getRoom(), this);
    }


    public Ship() {
        super();
        Panko.getInputMultiplexer().addProcessor(this);
    }

    @Override
    public void update() {
        super.update();
        moveShipBasedOnKeyPress();
        Panko.getMainCamera().position.set(getX(), getY(), 0);
    }

    private void moveShipBasedOnKeyPress() {

        if (System.currentTimeMillis() < lastMoveKeyMillis + 25) return;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            accelerate(1);
            lastMoveKeyMillis = System.currentTimeMillis();
            if (System.currentTimeMillis() > lastAccelerateMillis + 5000) {
                LD31Sounds.engine.play();
                createEngineSmoke(2);
                createEngineSmoke(3);
                createEngineSmoke(5);
            }
            lastAccelerateMillis = System.currentTimeMillis();
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
    public String getTextureResourceName() {
        return "ship.png";
    }

    public static Ship newPlayerShip(PankoRoom room) {
        Ship result = new Ship();
        result.setPlayer(true);
        result.setRadius(1000d);
        return result;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
