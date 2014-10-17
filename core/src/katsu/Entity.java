package katsu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld28.Objective;
import ld28.Sounds;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class Entity {

    public long age = 0;

    public Game game = Util.getGame();
    public Sounds sounds = Util.getSounds();
    public UI ui = Util.getUI();
    public boolean activated = true;
    public boolean selected = false;
    public Room room;
    public boolean isCollisionTarget = false;
    public Color color;
    public boolean orientSpriteByMovement = true;

    public Objective currentObjective = Objective.NOTHING;
    public Entity targetEntity;

    public int x;
    public int y;

    public int newX;
    public int newY;

    public boolean wantsMove = false;
    public boolean wantsDestroy = false;

    public int dx = 0;
    public int dy = 0;

    public int width = Settings.tileWidth;
    public int height = Settings.tileHeight;

    public int maxhealth = 100;
    public int health = maxhealth;

    public long lastMoved = 0;

    public TextureRegion textureRegion = null;

    public boolean solid = true;
    public boolean beingDestroyed;
    public KatsuOrientation orientation;

    public void render(int screenX, int screenY, Graphics g, double pixelHorzScale, double pixelVertScale, SpriteBatch batch) {

        if (color != null) {
            batch.setColor(color);
        }

        if (textureRegion != null) {
            int x = screenX;
            int y = screenY;
            long rotation = 0; //System.currentTimeMillis()/100;
            rotation = rotation % 360;
            if (orientation != null) {
                if (orientation == KatsuOrientation.UP) rotation += 270;
                if (orientation == KatsuOrientation.DOWN) rotation += 90;
                if (orientation == KatsuOrientation.LEFT) rotation += 180;
                if (orientation == KatsuOrientation.RIGHT) rotation += 0;
            }
            rotation = rotation % 360;

            //Katsu.game.spriteBatch.draw(textureRegion, x, y, (int) (width * pixelHorzScale), (int) (height * pixelHorzScale), (int) (width * pixelHorzScale), (int) (height * pixelHorzScale), 1, 1, rotation);
            Katsu.game.spriteBatch.draw(textureRegion, x, y, width/2, height/2, width, height, (float)pixelHorzScale, (float)pixelVertScale, rotation);
            if (color != null) {
                batch.setColor(Color.WHITE);
            }
        } else {

            Game.shapeRenderer.setColor(Color.MAGENTA);
            Game.shapeRenderer.rect(screenX, screenY, (int) (Settings.tileWidth * pixelHorzScale), (int) (Settings.tileHeight * pixelVertScale));
        }

        if (ui.showHealth && (health < maxhealth)) {
            double dblhealth = (double) health / (double) maxhealth;
            // Green
            Color rectColor = new Color(0, 255, 0, 0.75f);

            if (dblhealth < 0.5d) rectColor = new Color(255, 255, 0, 0.75f);
            if (dblhealth < 0.25d) rectColor = new Color(255, 0, 0, 0.75f);

            Game.shapeRenderer.setColor(rectColor);
            Game.shapeRenderer.rect(screenX, screenY + (int) ((Settings.tileHeight - ui.healthBarSize) * pixelVertScale), (int) (Settings.tileWidth * pixelHorzScale * dblhealth), (int) (ui.healthBarSize * pixelVertScale));
        }
        if (!activated) {
            Color rectColor = new Color(255, 0, 0, 0.5f);
            Game.shapeRenderer.setColor(rectColor);
            Game.shapeRenderer.rect(screenX, screenY, (int) (Settings.tileWidth * pixelHorzScale), (int) (Settings.tileHeight * pixelVertScale));
        }


        if (selected) {
            Color c = new Color(Color.CYAN);

            c.a = 0.5f + 0.5f * (float) Math.sin(3f * Game.tick / (float) Settings.targetFrameRate);

            // TODO: hack to workaround issue introduced after changing texture rendering in LD28
            screenX -= 8;
            screenY -= 8;

            Game.uiShapeRenderer.setColor(c);
            int selectBorderSize = 1;
            // Top border
            Game.uiShapeRenderer.rect(screenX, screenY, (int) (Settings.tileWidth * pixelHorzScale), (int) (selectBorderSize * pixelVertScale));
            // Bottom border
            Game.uiShapeRenderer.rect(screenX, screenY + (int) ((Settings.tileHeight - selectBorderSize) * pixelVertScale), (int) (Settings.tileWidth * pixelHorzScale), (int) (selectBorderSize * pixelVertScale));
            // Left border
            Game.uiShapeRenderer.rect(screenX, screenY, (int) (selectBorderSize * pixelVertScale), (int) (Settings.tileHeight * pixelVertScale));
            // Right border
            Game.uiShapeRenderer.rect(screenX + (int) ((Settings.tileWidth - selectBorderSize) * pixelHorzScale), screenY, (int) (selectBorderSize * pixelVertScale), (int) (Settings.tileHeight * pixelVertScale));
        }

    }

    public void update(Application gc) {
        age++;
        if (health < 0) {
            wantsDestroy = true;
        }
        if ((dx != 0) || (dy != 0)) moveRelative(dx, dy);
    }

    public void moveRelative(int dx, int dy) {
        moveRelativePixels(dx * Settings.tileWidth, dy * Settings.tileHeight);
    }

    @Override
    public String toString() {
        String result = "";
        result = this.getClass().getSimpleName();


        if (currentObjective != Objective.NOTHING) {
            result += ". Thinking: " + currentObjective.toString();
            if (targetEntity != null) {
                result += " " + targetEntity.getClass().getSimpleName();
            }
        }

        return result;
    }

    public void moveRelativePixels(int dx, int dy) {
        setWantsMove(x + dx, y + dy);
    }

    protected void setWantsMove(int newX, int newY) {

        wantsMove = true;
        this.newX = newX;
        this.newY = newY;

    }

    public void randomMove() {

        if (System.currentTimeMillis() - lastMoved < 100) return;

        int dir = Util.getRandom().nextInt(4);
        switch (dir) {
            case 0:
                moveRelativePixels(0, 16);
                break;
            case 1:
                moveRelativePixels(0, -16);
                break;
            case 2:
                moveRelativePixels(16, 0);
                break;
            case 3:
                moveRelativePixels(-16, 0);
                break;
        }
    }


    public abstract boolean collide(Entity other);

    public int getResourceCost(Class c) {
        return 0;
    }


    public void beforeDeath(Room room) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void say(String s) {
        ui.writeText("<" + this.toString() + "> " + s);
    }

    public boolean isOnTopOf(Class clazz) {
        return getInstanceUnderneath(clazz) != null;
    }

    public Entity getInstanceUnderneath(Class clazz) {
        ArrayList<Entity> elist = room.findEntitiesAtPoint(x + 2, y + 2);
        for (Entity e : elist) {
            if (clazz.isInstance(e)) return e;
        }
        return null;
    }

    public void afterMoved() {

    }

}
