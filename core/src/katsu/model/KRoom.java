package katsu.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import katsu.K;
import katsu.input.KInputProcessor;
import katsu.spatial.KSpatialMap;
import katsu.spatial.KTiledMapLoader;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by shaun on 16/11/2014.
 */
public class KRoom extends KInputProcessor {

    @Getter @Setter private boolean active;
    @Getter @Setter private int fogX = 0;
    @Getter @Setter private int fogY = 0;
    @Getter @Setter private int fogRadius = 0;
    @Getter @Setter private boolean renderFog = false;
    @Getter @Setter private int gridWidth = 0;
    @Getter @Setter private int gridHeight = 0;
    @Getter @Setter private ArrayList<KEntity> entities;
    @Getter @Setter private ArrayList<KEntity> newEntities;
    @Getter @Setter private KSpatialMap spatialMap = new KSpatialMap();

    public KRoom() {
        super();
        wipeData();
    }

    public void wipeData() {
        entities = new ArrayList<KEntity>();
        newEntities = new ArrayList<KEntity>();
        spatialMap.wipeData();
    }

    public void loadFromTiledMap(String filename) {

        KTiledMapLoader loader = new KTiledMapLoader();
        loader.setFilename(filename);
        loader.loadToRoom(this);

    }

    public KEntity nearestEntityOf(Class clazz, KEntity toEntity) {

        KEntity result = null;

        long nearestDistance = 99999999;

        List<KEntity> entities = getEntities();
        for (KEntity e : entities) {
            if (clazz.isInstance(e)) {

                int dx = Math.abs(toEntity.getX() - e.getX());
                int dy = Math.abs(toEntity.getY() - e.getY());
                long dist = Math.round(Math.sqrt(dx * dx + dy * dy));
                if (dist < nearestDistance) {
                    nearestDistance = dist;
                    result = e;
                }
            }
        }

        return result;

    }

    public KEntity firstInstanceOfClass(Class clazz) {
        for (KEntity e : entities) {
            if (clazz.isInstance(e)) {
                return e;
            }
        }
        return null;
    }

    public void start() {
        setActive(true);
        K.input.getMultiplexer().addProcessor(this);
        entities = new ArrayList<KEntity>();
        newEntities = new ArrayList<KEntity>();
    }

    public void render() {
        K.graphics.logFPS();
        handleDestroyedEntities();
        handleNewEntities();
        sortEntitiesByZIndex();
        renderEachEntity();
    }

    private void renderEachEntity() {

        for (KEntity e : entities) {
            if (!isFogged(e)) {
                e.render();
            }
        }

    }

    private void sortEntitiesByZIndex() {
        // render lowest zLayer first
        Collections.sort(entities, new Comparator<KEntity>() {
            public int compare(KEntity o1, KEntity o2) {
                if (o1.getAppearance().getZLayer() == o2.getAppearance().getZLayer())
                    return 0;
                return o1.getAppearance().getZLayer() < o2.getAppearance().getZLayer() ? -1 : 1;
            }
        });
    }

    private void handleNewEntities() {
        for (KEntity e : newEntities) {
            entities.add(e);
        }
        newEntities.clear();
    }

    private void handleDestroyedEntities() {
        List<KEntity> destroyList = new ArrayList<KEntity>();
        for (KEntity e : entities) {
            if (e.isDestroyed()) {
                destroyList.add(e);
            }
        }
        for (KEntity e : destroyList) {
            spatialMap.handleEntityDelete(e);
            entities.remove(e);
            newEntities.remove(e);
        }
    }

    private boolean isFogged(KEntity e) {
        if (isRenderFog()) {
            int dx = e.getX() - getFogX();
            int dy = e.getY() - getFogY();
            long d = Math.round(Math.sqrt(dx * dx + dy * dy));
            return d > getFogRadius();
        } else {
            return false;
        }
    }

    public void update() {
        for (KEntity e : entities) {
            boolean canUpdate = true;
            if (isFogged(e)) canUpdate = false;
            if (e.isUpdateAsRogueLike()) {
                if (e.getLastUpdate() >= K.game.getLastRogueUpdate()) {
                    canUpdate = false;
                }
            }
            if (canUpdate) {
                e.update();
            }
        }
    }

    public void addNewEntity(KEntity entity) {
        entity.setRoom(this);
        newEntities.add(entity);
    }

    public ArrayList<KEntity> findEntitiesAtPoint(int x, int y) {
        return findEntitiesAtPoint(entities, x, y);
    }

    public ArrayList<KEntity> findEntitiesAtPoint(List<KEntity> entities, int x, int y) {

        ArrayList<KEntity> result = new ArrayList<KEntity>();

        for (KEntity e : entities) {

            // Quick optimization
            if (
                e.getX() > x || e.getY() > y ||
                e.getX() < x - e.getWidth() || e.getY() < y - e.getHeight()
            ) continue;

            Rectangle r = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());

            if (r.contains(x, y)) {
                result.add(e);
            }

        }
        return result;

    }

    public KEntity findFirstEntity(Class clazz, int gridX, int gridY) {
        List<KEntity> entities = findEntitiesAtPoint(gridX * K.settings.getGridSize(), gridY * K.settings.getGridSize());
        for (KEntity e : entities) {
            if (e.getGrid().getX() == gridX && e.getGrid().getY() == gridY && clazz.isInstance(e)) return e;
        }
        return null;
    }

    public List<KEntity> entitiesAtScreenPoint(int screenX, int screenY) {
        Vector3 clickLocation = new Vector3(screenX, screenY, 0);
        Vector3 worldLocation = K.graphics.camera.unproject(clickLocation);
        return findEntitiesAtPoint(Math.round(worldLocation.x), Math.round(worldLocation.y));
    }

    public void bringEntitiesToTop(Class clazz) {

        List<KEntity> toPop = new ArrayList<>();

        for (KEntity e: getEntities()) {
            if (clazz.isInstance(e)) {
                toPop.add(e);
            }
        }

        for (KEntity e: toPop) {
            getEntities().remove(e);
            getEntities().add(e);
        }

    }



}
