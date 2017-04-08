package mini73.rooms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import katsu.K;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by shaun on 15/12/13.
 */
public class StarField {

    public static ShapeRenderer shapeRenderer;

    static ArrayList<Point2D.Float> stars = new ArrayList<Point2D.Float>();
    static {

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(K.graphics.camera.combined);

        for (int i = 0; i< 1000; i++) {
            Point2D.Float newStar = new Point2D.Float();
            newStar.setLocation(K.random.nextInt(K.settings.getHres()), K.random.nextInt(K.settings.getVres()));
            stars.add(newStar);
        }
    }

    public static void render() {
        int depth = 0;

        MainRoom r = (MainRoom) K.game.roomForClass(MainRoom.class);
        int shipX = (r.ship.getX() / 16) % K.settings.getHres();
        int shipY = (r.ship.getX() / 16) % K.settings.getVres();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        int c = 0;
        for (Point2D.Float star : stars) {
            c++;

            float moveScale = 4f;
            shapeRenderer.setColor(Color.WHITE);
            if (c % 3 == 0) {
                moveScale = 2f;
                shapeRenderer.setColor(Color.GRAY);
            }
            if (c % 5 == 0) {
                moveScale = 1f;
                shapeRenderer.setColor(Color.DARK_GRAY);
            }

            if (c % 100 == 0) {
                moveScale = 1f;
                shapeRenderer.setColor(Color.YELLOW);
            }

            float actualX = (float)star.getX() - shipX * moveScale;
            float actualY = (float)star.getY() - shipY * moveScale;

            if (actualX < 0) actualX += K.settings.getHres();
            if (actualY < 0) actualY += K.settings.getVres();
            if (actualX > K.settings.getHres()) actualX -= K.settings.getHres();
            if (actualY > K.settings.getVres()) actualY -= K.settings.getVres();

            shapeRenderer.point(actualX, actualY, 0f);
        }
        shapeRenderer.end();
    }
}
