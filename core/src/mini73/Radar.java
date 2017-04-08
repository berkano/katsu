package mini73;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import katsu.K;
import mini73.rooms.MainRoom;

/**
 * Created by shaun on 15/12/13.
 */
public class Radar {

    public static ShapeRenderer shapeRenderer;

    TeleportMap teleportMap = TeleportMap.instance();

    static {

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(K.graphics.camera.combined);

    }

    public void render() {


        MainRoom r = (MainRoom) K.game.roomForClass(MainRoom.class);

        int shipX = (r.ship.getX() / 16) % K.settings.getHres();
        int shipY = (r.ship.getY() / 16) % K.settings.getVres();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        long time = System.currentTimeMillis() % 5000;
        float brightness = (5000 - time)/5000f;

        for (Teleport t : teleportMap.teleportArrayList) {

//            Point2D.Double shipScreenPoint =  r.mainView.entityPositionToScreenPoint(r.ship);
//            Point2D.Double teleportScreenPoint = r.mainView.mapPositionToScreenPoint(new Point2D.Float(t.x*16, t.y*16));

//            double dx = teleportScreenPoint.getX() - K.settings.getHres() / 2; //shipScreenPoint.getX();
//            double dy = teleportScreenPoint.getY() - K.settings.getVres() / 2; //shipScreenPoint.getY();

//            double blobX = K.settings.getHres()/2 + dx / 10; //shipScreenPoint.getX() + dx / 10;
//            double blobY = K.settings.getVres()/2 + dy / 10; //shipScreenPoint.getY() + dy / 10;


//            if (t.discovered) {
//                shapeRenderer.setColor(0f, brightness, 0f, 1f);
//            } else {
//                shapeRenderer.setColor(0f, 0f, brightness, 1f);
//            }
//            if (t.dock) {
//                shapeRenderer.circle((float) blobX, (float) blobY, 3);
//            }


            //shapeRenderer.line((float)shipScreenPoint.getX(), (float)shipScreenPoint.getY(), (float)teleportScreenPoint.getX(), (float)teleportScreenPoint.getY());
        }
        shapeRenderer.end();
    }
}
