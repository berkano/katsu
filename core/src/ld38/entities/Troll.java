package ld38.entities;

import katsu.KEntity;
import katsu.KTiledMapEntity;
import ld38.TrollNamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Troll extends TrollCastleEntityBase {

    public String name;

    Logger logger = LoggerFactory.getLogger(Troll.class);

    public Troll() {
        super();

        name = namer.nextName();
        logger.info("A new Troll called " + name + " appeared!");



    }

}
