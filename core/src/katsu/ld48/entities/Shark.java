package katsu.ld48.entities;

import katsu.model.KEntity;
import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Shark extends LD48EntityBase {

    public Shark(){
        super();
        setSolid(true);
    }

    @Override
    public void update() {
        super.update();


        KEntity target = getTargetEntity();
        if (target != null) {
            if (target instanceof Merson) {
                int dx = target.getX() - getX();
                int dy = target.getY() - getY();
                getPathFinderNextDirection();
            }
        }

    }
}
