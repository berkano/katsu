package katsu.spatial;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import katsu.K;
import katsu.model.KEntity;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class KTiledMapProcessor {

    @Getter
    @Setter
    private ArrayList<KEntity> entities;
    @Getter
    @Setter
    private TiledMap map;

    private String filename;
    private List<Class> classLookup;

    Logger logger = LoggerFactory.getLogger(KTiledMapProcessor.class);

    public KTiledMapProcessor(String filename, List<Class> classLookup) {
        this.classLookup = classLookup;
        this.filename = filename;
    }

    public KTiledMapProcessor loadFromMap() {

        setMap(loadMap(filename));
        entities = new ArrayList<>();

        List<TiledMapTileLayer> layerList = getLayersFromMap(getMap());

        MapProperties prop = getMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        for (TiledMapTileLayer currentLayer : layerList) {
            if (currentLayer == null) continue;
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    populateEntity(x, y, currentLayer);
                }
            }
        }

        logger.info(entities.size() + " entities loaded from map: " + filename);
        return this;
    }

    private void populateEntity(int x, int y, TiledMapTileLayer layer) {

        TiledMapTileLayer.Cell cell = layer.getCell(x, y);
        if (cell == null) return;
        TiledMapTile tiledMapTile = cell.getTile();
        if (tiledMapTile == null) return;
        int tileId = tiledMapTile.getId();
        if (tileId == 0) return;

        String entityId = (String) tiledMapTile.getProperties().get("entity");

        if (entityId != null && !entityId.equals("")) {
            Class c = null;
            for (Class clazz : classLookup) {
                if (clazz.getSimpleName().equals(entityId)) {
                    c = clazz;
                }
            }
            if (c != null) {
                createEntityFromClass(x, y, layer, c);
            }
        }
    }

    private void createEntityFromClass(int x, int y, TiledMapTileLayer layer, Class c) {

        // TODO load textures directly from tileset at map loading time
        //  eliminating the need for special "no-populate" layers
        if (K.textureCache.get(c) == null) {
            TextureRegion textureRegion = layer.getCell(x, y).getTile().getTextureRegion();
            K.textureCache.put(c, textureRegion);
        }

        try {
            KEntity e = (KEntity) c.newInstance();

            e.setX(x * K.settings.getGridSize());
            e.setY(y * K.settings.getGridSize());
            e.getAppearance().setTextureRegion(K.textureCache.get(c));
            entities.add(e);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private List<TiledMapTileLayer> getLayersFromMap(TiledMap map) {

        List<TiledMapTileLayer> layerList = new ArrayList<TiledMapTileLayer>();

        // In order of instantiation
        layerList.add((TiledMapTileLayer) map.getLayers().get("terrain"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("objects"));
        return layerList;

    }

    private TiledMap loadMap(String name) {
        try {
            TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();

            String resourceName = K.resource.relativeResource("maps/" + name + ".tmx");
            return new TmxMapLoader().load(resourceName, parameters);
        } catch (Exception ex) {
            K.game.exitDueToException("Failed to load tiled map: " + name, ex);
            return null;
        }
    }

}
