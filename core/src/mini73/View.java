package mini73;

import katsu.KEntity;

import java.awt.geom.Point2D;

/**
 * Created by shaun on 19/03/2017.
 */
public class View {
    public int portWidth;
    public int portHeight;
    public KEntity following;
    public int screenWidth;
    public int screenHeight;
    public int portX;
    public int portY;

    public Point2D.Double entityPositionToScreenPoint(KEntity entity) {
        UnfinishedBusinessException.raise();
        return null;
    }

    public Point2D.Double mapPositionToScreenPoint(Point2D.Float aFloat) {
        UnfinishedBusinessException.raise();
        return null;
    }
}
