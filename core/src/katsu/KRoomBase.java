package katsu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Rectangle;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import java.util.*;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class KRoomBase implements KRoom, InputProcessor {

    protected ArrayList<KEntity> entities;
    protected ArrayList<KEntity> newEntities;
    private boolean active;
    private FPSLogger fpsLogger = new FPSLogger();

    // Spatial indexing of entities
    SpatialIndex si = new RTree();
    int lastID = 0;
    HashMap<Integer, KEntity> idToEntity = new HashMap<Integer, KEntity>();
    HashMap<Integer, net.sf.jsi.Rectangle> idToRectangle = new HashMap<Integer, net.sf.jsi.Rectangle>();
    HashMap<KEntity, Integer> entityToID = new HashMap<KEntity, Integer>();

    @Override
    public void updateSpatialMap(KEntity entity) {
        // Remove any existing entries in the index
        Integer id = entityToID.get(entity);
        if (id != null) {
            si.delete(idToRectangle.get(id), id);
        }

        // Create rectangle
        net.sf.jsi.Rectangle rect = new net.sf.jsi.Rectangle(entity.getX(), entity.getY(), entity.getX() + entity.getWidth(), entity.getY() + entity.getHeight());

        id = lastID;
        lastID++;

        // Add to spatial index
        si.add(rect, id);

        // Keep track of rectangles and entities for later
        idToEntity.put(id, entity);
        idToRectangle.put(id, rect);

    }


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

    @Override
    public void start() {
        setActive(true);
        K.getInputMultiplexer().addProcessor(this);
        entities = new ArrayList<KEntity>();
        newEntities = new ArrayList<KEntity>();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void render() {

        if (K.getSettings().isLogFPS()) {
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
                if (o1.getzLayer() == o2.getzLayer())
                    return 0;
                return o1.getzLayer() < o2.getzLayer() ? -1 : 1;
            }
        });

        for (KEntity e : entities) {
            e.render();
        }
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this;
    }

    @Override
    public void setEntities(ArrayList<KEntity> entities) {
        this.entities = entities;
    }

    @Override
    public ArrayList<KEntity> getEntities() {
        return entities;
    }

    public KRoomBase() {
        super();
        si.init(null);
    }

    @Override
    public void update() {
        for (KEntity e : entities) {
            e.update();
        }
    }

    @Override
    public void addNewEntity(KEntity entity) {
        newEntities.add(entity);
    }

    @Override
    public List getNewEntities() {
        return newEntities;
    }

    @Override
    public ArrayList<KEntity> findEntitiesAtPoint(int x, int y) {

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
