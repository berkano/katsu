package ld28;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import katsu.Katsu;
import katsu.Settings;
import ld28.rooms.MainRoom;

import java.awt.geom.Point2D;

/**
 * Created by shaun on 15/12/13.
 */
public class Radar {

    public static ShapeRenderer shapeRenderer;

    static {

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(Katsu.game.camera.combined);

    }

    public static void render() {


        MainRoom r = (MainRoom) Katsu.game.currentRoom;

        int shipX = (r.ship.x / 16) % Settings.getHres();
        int shipY = (r.ship.y / 16) % Settings.getVres();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        long time = System.currentTimeMillis() % 5000;
        float brightness = (5000 - time)/5000f;



        for (Teleport t : TeleportMap.teleportArrayList) {

            Point2D.Double shipScreenPoint = r.mainView.entityPositionToScreenPoint(r.ship);
            Point2D.Double teleportScreenPoint = r.mainView.mapPositionToScreenPoint(new Point2D.Float(t.x*16, t.y*16));

            double dx = teleportScreenPoint.getX() - Settings.getHres() / 2; //shipScreenPoint.getX();
            double dy = teleportScreenPoint.getY() - Settings.getVres() / 2; //shipScreenPoint.getY();

            double blobX = Settings.getHres() /2 + dx / 10; //shipScreenPoint.getX() + dx / 10;
            double blobY = Settings.getVres() /2 + dy / 10; //shipScreenPoint.getY() + dy / 10;


            if (t.discovered) {
                shapeRenderer.setColor(0f, brightness, 0f, 1f);
            } else {
                shapeRenderer.setColor(0f, 0f, brightness, 1f);
            }
            if (t.dock) {
                shapeRenderer.circle((float) blobX, (float) blobY, 3);
            }


            //shapeRenderer.line((float)shipScreenPoint.getX(), (float)shipScreenPoint.getY(), (float)teleportScreenPoint.getX(), (float)teleportScreenPoint.getY());
        }
        shapeRenderer.end();
    }
}
