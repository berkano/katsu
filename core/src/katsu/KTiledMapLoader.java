package katsu;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 31/07/2016.
 */
public class KTiledMapLoader {

    @Getter @Setter private String filename;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void loadToRoom(KRoom room) {

        KTiledMapData data = new KTiledMapData(filename, K.runner.getClassLookup());
        data.loadFromMap();

        room.setEntities(data.getEntities());

        for (KEntity e : room.getEntities()) {
            e.setRoom(room);
        }

        room.setGridHeight(data.getMap().getProperties().get("height", Integer.class));
        room.setGridWidth(data.getMap().getProperties().get("width", Integer.class));

    }
}
