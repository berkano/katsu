package ld31v2.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import ld31v2.WarGame;
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
    public void update() {
        super.update();
        if (selectedEntity != null) {
            setX(selectedEntity.getX());
            setY(selectedEntity.getY());
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldClickPoint = Panko.getMainCamera().unproject(new Vector3(screenX, screenY, 0));


        PankoEntity candidateSelection = null;

        for (PankoEntity e : getRoom().getEntities()) {
            Rectangle rect = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
            if (rect.contains(worldClickPoint.x, worldClickPoint.y)) {
                candidateSelection = e;
            }
        }

        if (candidateSelection != null) {

            if (candidateSelection instanceof ControllableMob) {
                selectedEntity = candidateSelection;
                setX(selectedEntity.getX());
                setY(selectedEntity.getY());
                WarGame.vox1.play();
                //Panko.getUI().writeText("Selected " + selectedEntity.getClass().getSimpleName());
            } else {
                //Panko.getUI().writeText("That is a "+candidateSelection.getClass().getSimpleName());
                if (selectedEntity != null) {
                    selectedEntity.setTarget(candidateSelection);
                    WarGame.vox2.play();
//                    selectedEntity.setX(candidateSelection.getX());
//                    selectedEntity.setY(candidateSelection.getY());
//                    Panko.queueEntityToTop(selectedEntity);
                }
            }
        }

        return false;
    }


}
