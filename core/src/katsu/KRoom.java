package katsu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Rectangle;
import gnu.trove.procedure.TIntProcedure;
import lombok.Getter;
import lombok.Setter;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import java.util.*;

/**
 * Created by shaun on 16/11/2014.
 */
public class KRoom implements InputProcessor {

    @Getter @Setter private boolean active;
    @Getter @Setter private int fogX = 0;
    @Getter @Setter private int fogY = 0;
    @Getter @Setter private int fogRadius = 0;
    @Getter @Setter private boolean renderFog = false;
    @Getter @Setter private int gridWidth = 0;
    @Getter @Setter private int gridHeight = 0;
    @Getter @Setter private ArrayList<KEntity> entities;
    @Getter @Setter private ArrayList<KEntity> newEntities;
    private FPSLogger fpsLogger = new FPSLogger();

    // Spatial indexing of entities
    private SpatialIndex si;
    private int lastID;
    private HashMap<Integer, KEntity> idToEntity;
    private HashMap<Integer, net.sf.jsi.Rectangle> idToRectangle;
    private HashMap<KEntity, Integer> entityToID;

    public void wipeData() {
        entities = new ArrayList<KEntity>();
        newEntities = new ArrayList<KEntity>();
        idToEntity = new HashMap<Integer, KEntity>();
        idToRectangle = new HashMap<Integer, net.sf.jsi.Rectangle>();
        entityToID = new HashMap<KEntity, Integer>();
        si = new RTree();
        si.init(null);
        lastID = 0;
    }

    public void updateSpatialMap(KEntity entity) {
        // Remove any existing entries in the index
        Integer id = entityToID.get(entity);
        if (id != null) {
            si.delete(idToRectangle.get(id), id);
        }

        // Create rectangle
        net.sf.jsi.Rectangle rect = new net.sf.jsi.Rectangle(entity.getX(), entity.getY(), entity.getX() + entity.getWidth() - 1, entity.getY() - entity.getHeight() + 1);

        id = lastID;
        lastID++;

        // Add to spatial index
        si.add(rect, id);

        // Keep track of rectangles and entities for later
        idToEntity.put(id, entity);
        idToRectangle.put(id, rect);
        entityToID.put(entity, id);

    }

    
    public List<KEntity> spatialSearchByIntersection(net.sf.jsi.Rectangle rect) {
        ArrayList<KEntity> results = new ArrayList<KEntity>();
        SaveToListProcedure myProc = new SaveToListProcedure();
        si.intersects(rect, myProc);
        for (int id : myProc.getIds()) {
            results.add(idToEntity.get(id));
        }
        return results;
    }

    public void loadRoomFromTMX(String tmxName) {

        KTmxData data = new KTmxData(tmxName, K.runner.getClassLookup());
        data.loadFromMap();

        setEntities(data.getEntities());

        for (KEntity e : getEntities()) {
            e.setRoom(this);
        }

        setGridHeight(data.getMap().getProperties().get("height", Integer.class));
        setGridWidth(data.getMap().getProperties().get("width", Integer.class));
    }

    class SaveToListProcedure implements TIntProcedure {
        private List<Integer> ids = new ArrayList<Integer>();

        public boolean execute(int id) {
            ids.add(id);
            return true;
        };

        private List<Integer> getIds() {
            return ids;
        }
    };

    public void createInstancesAtAll(Class find, Class toAdd) {
        for (KEntity e : entities) {
            if (find.isInstance(e)) {
                try {
                    KEntity newEntity = (KEntity)toAdd.newInstance();
                    newEntity.setX(e.getX());
                    newEntity.setY(e.getY());
                    newEntities.add(newEntity);
                } catch (Exception ex) {

                }
            }
        }
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

        if (K.settings.isLogFPS()) {
            fpsLogger.log();
        }

        List<KEntity> destroyList = new ArrayList<KEntity>();
        for (KEntity e : entities) {
            if (e.isDestroyed()) {
                destroyList.add(e);
            }
        }

        for (KEntity e : destroyList) {
            Integer id = entityToID.get(e);
            // Cleanup spatial index
            if (id != null) {
                si.delete(idToRectangle.get(id), id);
                idToRectangle.remove(id);
                idToEntity.remove(id);
                entityToID.remove(e);
            }
            entities.remove(e);
            newEntities.remove(e);
        }

        for (KEntity e : newEntities) {
            entities.add(e);
        }

        newEntities.clear();

        // render lowest zLayer first
        Collections.sort(entities, new Comparator<KEntity>() {
            public int compare(KEntity o1, KEntity o2) {
                if (o1.getAppearance().getZLayer() == o2.getAppearance().getZLayer())
                    return 0;
                return o1.getAppearance().getZLayer() < o2.getAppearance().getZLayer() ? -1 : 1;
            }
        });

        for (KEntity e : entities) {
            if (!isFogged(e)) {
                e.render();
            }
        }
    }

    private boolean isFogged(KEntity e) {
        if (isRenderFog()) {
            int dx = e.getX() - getFogX();
            int dy = e.getY() - getFogY();
            long d = Math.round(Math.sqrt(dx * dx + dy * dy));
            if (d > getFogRadius()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public KRoom() {
        super();
        wipeData();
    }

    public void update() {
        for (KEntity e : entities) {
            boolean canUpdate = true;
            if (isFogged(e)) canUpdate = false;
            if (e.isUpdateAsRogueLike()) {
                if (e.getLastUpdate() >= K.runner.getLastRogueUpdate()) {
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
            if (e.getX() > x) continue;
            if (e.getY() > y) continue;
            if (e.getX() < x - e.getWidth()) continue;
            if (e.getY() < y - e.getHeight()) continue;

            Rectangle r = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());

            if (r.contains(x, y)) {
                result.add(e);
            }

        }
        return result;

    }

    // future use
    public boolean keyDown(int keycode) {
        return false;
    }
    public boolean keyUp(int keycode) {
        return false;
    }
    public boolean keyTyped(char character) {
        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    public boolean scrolled(int amount) {
        return false;
    }
}
