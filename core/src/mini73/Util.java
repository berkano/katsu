package mini73;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
public class Util {

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

    public static Music loadMusic(String name) {
        try {
            Music s = Gdx.audio.newMusic(getResource("music/" + name));
            return s;
        } catch (Exception ex) {
            return Gdx.audio.newMusic(getResource("sounds/generic-missing-sound.wav"));
        }
    }

    public static TiledMap loadMap(String name) {
        try {
            TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
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


    public static void tileEntityIntoRoom(KRoom r, Class c, int x, int y, int width, int height) {

        for (int tx = 0; tx < width; tx++) {
            for (int ty = 0; ty < height; ty++) {
                try {
                    KEntity e = (KEntity) c.newInstance();
                    e.setX(tx * K.settings.getGridSize() + x * K.settings.getGridSize());
                    e.setY(ty * K.settings.getGridSize() + y * K.settings.getGridSize());
                    r.addNewEntity(e);
                } catch (Exception ex) {
                    log(ex.getMessage());
                }
            }
        }


    }

    public static void loadFromTMX(KRoom room, String mapName) {
        long startMillis = System.currentTimeMillis();
        TiledMap map = loadMap(mapName + ".tmx");
        createEntitiesFromMap(room, map);
        long stopMillis = System.currentTimeMillis();
        log("Map loaded and processed in " + String.valueOf(stopMillis - startMillis) + " ms");
    }

    private static void createEntitiesFromMap(KRoom room, TiledMap map) {
        throw new UnfinishedBusinessException();
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
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), false);
        } else {
            Gdx.graphics.setDisplayMode(K.settings.getHres(), K.settings.getVres(), true);
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
        int sel = K.random.nextInt(musics.length);
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

        return i + K.random.nextInt(i1);

    }
}
