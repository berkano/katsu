package ld32.entities;

import ld32.LD32Sounds;
import panko.Panko;
import panko.PankoEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mole extends Mob {

    private long lastDig = Panko.currentTime();
    private int maxDigInterval = 500;
    public boolean invincible = true;
    private long invincibleUntil = Panko.currentTime();

    public Mole() {
        super();
        setMaxMoveInterval(100);
        lookAtMe();
    }

    @Override
    public void onMoved() {
        super.onMoved();
        LD32Sounds.mole_move.play();
        lookAtMe();
    }

    @Override
    public void update() {
        super.update();
        if (getInvincibleUntil() < Panko.currentTime()) {
            invincible = false;
        }
    }

    @Override
    public void render() {

        boolean shouldRender = true;
        // When not harmable flicker the rendering
        if (invincible) {
            if ((Panko.currentTime() % 500) < 250) shouldRender = false;
        }

         if (shouldRender) super.render();
        lookAtMe();
    }

    private void lookAtMe() {
        Panko.getMainCamera().position.x = getX();
        Panko.getMainCamera().position.y = getY();
    }

    public void digRequested() {
        if (lastDig < Panko.currentTime() - maxDigInterval) {
            if (getFacing() != null) {

                // Target point to check is the middle of me... then 1 grid size in the direction being faced
                int myMiddleX = getX() + getWidth() / 2;
                int myMiddleY = getY() + getHeight() / 2;
                int checkX = myMiddleX + getWidth() * getFacing().dx();
                int checkY = myMiddleY + getHeight() * getFacing().dy();

                ArrayList<PankoEntity> digEntities = getRoom().findEntitiesAtPoint(checkX, checkY);

                for (PankoEntity e : digEntities) {
                    if (e instanceof Dirt) {
                        e.createInPlace(EmptyDirt.class);
                        e.setHealth(0);
                        Panko.queueEntityToTop(this);
                        LD32Sounds.mole_dig.play();
                    }
                }

                lastDig = Panko.currentTime();
            }
        }

    }

    public long getInvincibleUntil() {
        return invincibleUntil;
    }

    public void setInvincibleUntil(long invincibleUntil) {
        this.invincibleUntil = invincibleUntil;
    }
}
