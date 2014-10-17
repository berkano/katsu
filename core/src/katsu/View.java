package katsu;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.geom.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public class View {

    public int screenX = 0;
    public int screenY = 0;

    public int portX = 0;
    public int portY = 0;

    public int portWidth = 768;
    public int portHeight = 768;

    public int screenWidth = 768;
    public int screenHeight = 768;

    public boolean visible = true;

    public Entity following;

    public Room room;

    public View(Room room) {
        this.room = room;
    }

    public Point2D.Double mapPositionToScreenPoint(Point2D mapPosition) {
        double pixelHorzScale = (double) screenWidth / portWidth;
        double pixelVertScale = (double) screenHeight / portHeight;

        double entityPortX = mapPosition.getX() - portX;
        double entityPortY = mapPosition.getY() - portY;

        int entityRelScreenX = (int) Math.round(entityPortX * pixelHorzScale);
        int entityRelScreenY = (int) Math.round(entityPortY * pixelVertScale);

        int entityScreenX = entityRelScreenX + screenX;
        int entityScreenY = entityRelScreenY + screenY;

        Point2D.Double result = new Point2D.Double(entityScreenX, entityScreenY);
        return result;

    }

    public Point2D.Double entityPositionToScreenPoint(Entity e) {

        return mapPositionToScreenPoint(new Point2D.Double(e.x, e.y));

    }

    public void render(Graphics g, SpriteBatch batch) {

        Logger.trace("View.render()");

        if (following != null) {
            portX = following.x - portWidth / 2;
            portY = following.y - portHeight / 2;
        }

        double pixelHorzScale = (double) screenWidth / portWidth;
        double pixelVertScale = (double) screenHeight / portHeight;

        for (Entity e : room.entities) {

            int entityPortX = e.x - portX;
            int entityPortY = e.y - portY;

            int entityRelScreenX = (int) Math.round(entityPortX * pixelHorzScale);
            int entityRelScreenY = (int) Math.round(entityPortY * pixelVertScale);

            int entityScreenX = entityRelScreenX + screenX;
            int entityScreenY = entityRelScreenY + screenY;

            // TODO: cleaner view clipping
            if ((entityScreenX >= screenX - e.width) && (entityScreenX <= screenX + screenWidth + e.width)) {
                if ((entityScreenY >= screenY - e.height) && (entityScreenY <= screenY + screenHeight + e.height)) {
                    e.render(entityScreenX, entityScreenY, g, pixelHorzScale, pixelVertScale, batch);
                }
            }

        }
    }

    public void update(Application gc) {


    }
}
