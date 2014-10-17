package katsu;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import ld28.Sounds;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public static Random getRandom() {
        return Game.getInstance().r;
    }

    public static void log(String s) {
        System.out.println(s);
    }

    public static String wrap(String in, int len) {
        in = in.trim();
        if (in.length() < len) return in;
        if (in.substring(0, len).contains("\n"))
            return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
        int place = Math.max(Math.max(in.lastIndexOf(" ", len), in.lastIndexOf("\t", len)), in.lastIndexOf("-", len));
        return in.substring(0, place).trim() + "\n" + wrap(in.substring(place), len);
    }

    public static FileHandle getResource(String fname) {
        FileHandle result = Gdx.files.getFileHandle(fname, Files.FileType.Internal);
        if (result == null) {
            throw new RuntimeException("Could not load resource " + fname);
        }
        return result;
    }

    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String loadTextResourceAsString(String name) {
        try {
                FileHandle fh = getResource("text/"+name);
                InputStream is = fh.read();
                String result = convertStreamToString(is);
                is.close();
                return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static Music loadMusic(String name) {
        try {
            Music s = Gdx.audio.newMusic(getResource("music/" + name));
            return s;
        } catch (Exception ex) {
            return Gdx.audio.newMusic(getResource("sounds/generic-missing-sound.wav"));
        }
    }

    public static Game getGame() {
        return Game.getInstance();
    }

    public static Sounds getSounds() {
        return Game.getInstance().sounds;
    }

    public static UI getUI() {
        return Game.getInstance().ui;
    }

    public static TiledMap loadMap(String name) {
        try {
            TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
            meta.commented();
            meta.bug("probably causing the map issues");
//            parameters.yUp = false;
            TiledMap tiledMap = new TmxMapLoader().load("maps/" + name, parameters);
            return tiledMap;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Sound loadSound(String name) {
        try {
            Sound s = Gdx.audio.newSound(getResource("sounds/" + name));
            return s;
        } catch (Exception ex) {
            return Gdx.audio.newSound(getResource("sounds/generic-missing-sound.wav"));
        }

    }


    public static void tileEntityIntoRoom(Room r, Class c, int x, int y, int width, int height) {

        for (int tx = 0; tx < width; tx++) {
            for (int ty = 0; ty < height; ty++) {
                try {
                    Entity e = (Entity) c.newInstance();
                    e.x = tx * Settings.tileWidth + x * Settings.tileWidth;
                    e.y = ty * Settings.tileHeight + y * Settings.tileHeight;
                    r.entities.add(e);
                } catch (Exception ex) {
                    log(ex.getMessage());
                }
            }
        }


    }

    public static void loadFromTMX(Room room, String mapName) {
        long startMillis = System.currentTimeMillis();
        TiledMap map = loadMap(mapName + ".tmx");
        createEntitiesFromMap(room, map);
        long stopMillis = System.currentTimeMillis();
        log("Map loaded and processed in " + String.valueOf(stopMillis - startMillis) + " ms");
    }

    private static void createEntitiesFromMap(Room room, TiledMap map) {

        List<TiledMapTileLayer> layerList = new ArrayList<TiledMapTileLayer>();
        //layerList.add((TiledMapTileLayer) map.getLayers().get("tiles"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("terrain"));
        layerList.add((TiledMapTileLayer) map.getLayers().get("objects"));
        //layerList.add((TiledMapTileLayer) map.getLayers().get("tileset-no-populate"));

        boolean cullEntities = true;
        if (getGame().currentLevel.equals("9999")) cullEntities = false;

        MapProperties prop = map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);

        for (TiledMapTileLayer currentLayer : layerList) {
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {

                    TiledMapTileLayer.Cell cell = currentLayer.getCell(x, y);

                    if (cell == null) continue;

                    TiledMapTile tiledMapTile = cell.getTile();
                    if (tiledMapTile == null) continue;
                    int tileId = tiledMapTile.getId();
                    if (tileId == 0) continue;

                    String entityId = (String) tiledMapTile.getProperties().get("entity");

                    if (entityId != "") {

                        Class c = room.classLookup.get(entityId);

                        if (c != null) {
                            try {

                                if (room.entityTextureRegions.get(c) == null) {
                                    TextureRegion textureRegion = tileStitch(x, y, currentLayer, 1, 1, map);
                                    room.entityTextureRegions.put(c, textureRegion);
                                }

                                if (currentLayer.getName().contains("no-populate")) {
                                    // Just used to load the textures.
                                } else {

                                    Entity e = (Entity) Util.newInstance(c);
                                    e.x = x * Settings.tileWidth;
                                    e.y = y * Settings.tileHeight;
                                    e.textureRegion = room.entityTextureRegions.get(c);
                                    e.room = room;
                                    room.entities.add(e);
                                }

                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                    }
                }
            }
        }


    }

    private static Object newInstance(Class c) {
        try {
            return c.newInstance();
        } catch (Exception ex) {
            throw new Error("Unable to instantiate class " + c.getName());
        }
    }

    public static void loopMusic(Music music) {
        music.play();
        music.setLooping(true);
    }

    public static void toggleFullScreenMode() {
        if (Gdx.graphics.isFullscreen()) {
            //Gdx.graphics.setDisplayMode(Game.instance.initialDisplayMode.width, Game.instance.initialDisplayMode.height, false);
            Gdx.graphics.setDisplayMode(Settings.hres, Settings.vres, false);
        } else {
            Gdx.graphics.setDisplayMode(Settings.hres, Settings.vres, true);
        }
    }


    public static TextureRegion tileStitch(int x, int y, TiledMapTileLayer tileLayer, int tilesWide, int tilesHigh, TiledMap map) {

        //TODO: support stitching
        return tileLayer.getCell(x, y).getTile().getTextureRegion();


        // Optimize common case!
//        if (tilesWide == 1 && tilesHigh == 1) {
//            return tileLayer.getCell(x, y).getTile().getTextureRegion().getTexture();
//            //return map.getTileImage(x, y, tileLayer);
//        }

//        Image stitchImg = null;

//        try {
//            // textureRegion consists of 4x4 tiles in the tilemap, so stitch it back together...
//            stitchImg = new Image(map.getTileWidth() * tilesWide * Settings.pixelScale, map.getTileHeight() * tilesHigh* Settings.pixelScale);
//
//            for (int tx = 0; tx < tilesWide; tx++) {
//                for (int ty = 0; ty < tilesHigh; ty++) {
//                    Image cellImage = map.getTileImage(tx + x, ty + y, tileLayer);
//
////                    public void drawTextureRegion(org.newdawn.slick.Image image, float screenX, float screenY, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2) { /* compiled code */ }
//
//                    float drawX = tx * map.getTileWidth() * Settings.pixelScale;
//                    float drawY = ty * map.getTileHeight() * Settings.pixelScale;
//                    float drawX2 = (tx+1) * map.getTileWidth() * Settings.pixelScale;
//                    float drawY2 = (ty+1) * map.getTileHeight() * Settings.pixelScale;
//                    float srcX = 0;
//                    float srcY = 0;
//                    float srcX2 = cellImage.getWidth();
//                    float srcY2 = cellImage.getHeight();
//
//                    //stitchImg.getGraphics().drawTextureRegion(cellImage, tx * map.getTileWidth(), ty * map.getTileHeight());
//                    stitchImg.getGraphics().drawTextureRegion(cellImage, drawX, drawY, drawX2, drawY2, srcX, srcY, srcX2, srcY2);
//                }
//            }
//            stitchImg.getGraphics().flush();
//        } catch (SlickException ex) {
//
//        }
//        return stitchImg;
//
////        Porting.mustImpl();
////        return null;

    }

    public static void playOneOf(Music... musics) {
        int sel = Game.instance.r.nextInt(musics.length);
        Music selM = musics[sel];
        selM.play();
        selM.setLooping(false);
    }

    public static void stopAll(Music... musics) {
        //To change body of created methods use File | Settings | File Templates.
        for (Music m : musics) {
            m.stop();
        }
    }

    public static Integer randomInRange(int i, int i1) {

        return i + getRandom().nextInt(i1);

    }
}
