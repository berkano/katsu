package ld31v2.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import panko.Panko;
import panko.PankoEntity;
import panko.PankoEntityBase;

/**
 * Created by shaun on 06/12/2014.
 */
public class Selection extends PankoEntityBase {

    private static PankoEntity selectedEntity;

    public Selection() {
        Panko.getInputMultiplexer().addProcessor(this);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldClickPoint = Panko.getMainCamera().unproject(new Vector3(screenX, screenY, 0));

        for (PankoEntity e : getRoom().getEntities()) {
            Rectangle rect = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            if (rect.contains(worldClickPoint.x, worldClickPoint.y)) {
                selectedEntity = e;
            }
        }

        if (selectedEntity != null) {
            setX(selectedEntity.getX());
            setY(selectedEntity.getY());
            Panko.getUI().writeText("Selected!");
        }

        return false;
    }


}
