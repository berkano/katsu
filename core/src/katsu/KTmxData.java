package katsu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ld34.entities.Snowman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class KTmxData {

    private ArrayList<KEntity> entities;
    private HashMap<Class, TextureRegion> entityTextureRegions;

    private String tiledMapFile;
    private TiledMap map;
    private HashMap<String, Class> classLookup;
    private int tileWidth = 16; // TODO determine from TMX
    private int tileHeight = 16;

    public KTmxData(String tmxFile, HashMap<String, Class> classLookup) {
        this.classLookup = classLookup;
        this.tiledMapFile = tmxFile;
    }

    public KTmxData loadFromMap() {

        setMap(loadMap(tiledMapFile));
        entities = new ArrayList<KEntity>();
        entityTextureRegions = K.getUI().getTextureCache(); // new HashMap<Class, TextureRegion>();

        List<TiledMapTileLayer> layerList = new ArrayList<TiledMapTileLayer>();


        // In order of instantiation
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("no-populate"));
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("invisible"));
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("background"));
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("background-ontop"));
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("terrain"));
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("objects"));
        layerList.add((TiledMapTileLayer) getMap().getLayers().get("passageways"));

        MapProperties prop = getMap().getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        for (TiledMapTileLayer currentLayer : layerList) {
            if (currentLayer == null) continue;
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {

                    TiledMapTileLayer.Cell cell = currentLayer.getCell(x, y);

                    if (cell == null) continue;

                    TiledMapTile tiledMapTile = cell.getTile();
                    if (tiledMapTile == null) continue;
                    int tileId = tiledMapTile.getId();
                    if (tileId == 0) continue;

                    String entityId = (String) tiledMapTile.getProperties().get("entity");

                    if (entityId != null && !entityId.equals("")) {

                        Class c = classLookup.get(entityId);

                        if (c != null) {
                            try {

                                TextureRegion textureRegion = K.getUI().tileStitch(x, y, currentLayer);

                                if (entityTextureRegions.get(c) == null) {
                                    entityTextureRegions.put(c, textureRegion);

                                }

                                if (!currentLayer.getName().contains("no-populate")) { // no-populate just used for loading textures.
                                    KEntity e = (KEntity) c.newInstance();
                                    e.setX(x * tileWidth);
                                    e.setY(y * tileHeight);
                                    e.setTextureRegion(entityTextureRegions.get(c));
                                    entities.add(e);
                                    if (e instanceof Snowman) {
                                        KLog.trace("Added Snowman to map from xy=" + x + "," + y);
                                    }
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                K.exitDueToException("Failed to process tiled map: " + tiledMapFile, ex);
                            }
                        }

                    }
                }
            }
        }

        KLog.trace(entities.size() + " entities loaded from map: " + tiledMapFile);
        return this;
    }

    private TiledMap loadMap(String name) {
        try {
            TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();

            String resourceName = KResource.relativeResource("maps/" + name + ".tmx");
            return new TmxMapLoader().load(resourceName, parameters);
        } catch (Exception ex) {
            K.exitDueToException("Failed to load tiled map: " + name, ex);
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
