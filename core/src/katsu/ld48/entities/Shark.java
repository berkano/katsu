package katsu.ld48.entities;

import katsu.K;
import katsu.model.KEntity;
import katsu.model.KTiledMapEntity;
import katsu.spatial.KDirection;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Shark extends LD48EntityBase {

    public Shark(){
        super();
        setSolid(true);
        setLeftMargin(4 / 2);
        setRightMargin(5 / 2);
        setTopMargin(9 / 2);
        setBottomMargin(13 / 2);
    }

    @Override
    public void update() {
        super.update();


        KEntity target = getTargetEntity();

        if (target != null) {
            if (target instanceof Merson) {

                if (distanceBetween(target, this) < 200) {

                    // periodically invalidate cached pathfinder direction
                    if ((K.random.nextInt(10)) ==0) {
                        setPathFinderNextDirection(null);
                    }

                    // performance/cache
                    if (getPathFinderNextDirection() == null) {
                        setPathFinderNextDirection(getGrid().doPathFinding(target.getGrid().getX(), target.getGrid().getY()));
                    }

                    KDirection suggestion = getPathFinderNextDirection();

                    if (suggestion != null) {
                        // Slow down very slightly
                        if (K.random.nextInt(8) != 0) {
                            tryMoveTowards(suggestion);
                            if (suggestion.getDx() <= 0) {
                                getAppearance().setSpriteFlip(false);
                            } else {
                                getAppearance().setSpriteFlip(true);
                            }
                        }
                    }

                }


            }
        }

    }

}
