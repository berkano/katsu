package katsu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class KTiledMapData {

    private ArrayList<KEntity> entities;
    private HashMap<Class, TextureRegion> entityTextureRegions;

    private String filename;
    private TiledMap map;
    private List<Class> classLookup;

    public KTiledMapData(String filename, List<Class> classLookup) {
        this.classLookup = classLookup;
        this.filename = filename;
    }

    public KTiledMapData loadFromMap() {

        setMap(loadMap(filename));
        entities = new ArrayList<KEntity>();
        entityTextureRegions = K.textureCache; // new HashMap<Class, TextureRegion>();

        List<TiledMapTileLayer> layerList = getLayersFromMap(getMap());

        MapProperties prop = getMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        for (TiledMapTileLayer currentLayer : layerList) {
            if (currentLayer == null) continue;
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    populateEntityFromTileOfLayer(x, y, currentLayer);
                }
            }
        }

        K.logger.trace(entities.size() + " entities loaded from map: " + filename);
        return this;
    }

    private void populateEntityFromTileOfLayer(int x, int y, TiledMapTileLayer layer) {

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
        if (entityTextureRegions.get(c) == null) {
            TextureRegion textureRegion = layer.getCell(x, y).getTile().getTextureRegion();
            entityTextureRegions.put(c, textureRegion);
        }
        if (!layer.getName().contains("no-populate")) { // no-populate just used for loading textures.
            try {
                KEntity e = (KEntity) c.newInstance();

                e.setX(x * K.settings.getGridSize());
                e.setY(y * K.settings.getGridSize());
                e.getAppearance().setTextureRegion(entityTextureRegions.get(c));
                entities.add(e);
            } catch (Exception ex) {
                throw new KException(ex);
            }
        }
    }

    private List<TiledMapTileLayer> getLayersFromMap(TiledMap map) {

        List<TiledMapTileLayer> layerList = new ArrayList<TiledMapTileLayer>();

        // In order of instantiation
        layerList.add((TiledMapTileLayer) map.getLayers().get("no-populate"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("invisible"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("background"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("background-ontop"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("terrain"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("objects"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("passageways"));

        return layerList;
    }

    private TiledMap loadMap(String name) {
        try {
            TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();

            String resourceName = K.resource.relativeResource("maps/" + name + ".tmx");
            return new TmxMapLoader().load(resourceName, parameters);
        } catch (Exception ex) {
            K.runner.exitDueToException("Failed to load tiled map: " + name, ex);
            return null;
        }
    }

    public ArrayList<KEntity> getEntities() {
        return entities;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }
}
