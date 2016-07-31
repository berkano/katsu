package katsu;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 31/07/2016.
 */
public class KTiledMapLoader {

    @Getter @Setter private String filename;

    public void loadToRoom(KRoom room) {

        KTiledMapProcessor processor = new KTiledMapProcessor(filename, K.runner.getClassLookup());
        processor.loadFromMap();

        room.setEntities(processor.getEntities());

        for (KEntity e : room.getEntities()) {
            e.setRoom(room);
        }

        room.setGridHeight(processor.getMap().getProperties().get("height", Integer.class));
        room.setGridWidth(processor.getMap().getProperties().get("width", Integer.class));

    }
}
