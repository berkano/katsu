package katsu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Rectangle;
import gnu.trove.procedure.TIntProcedure;
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
    private int fogX = 0;
    private int fogY = 0;
    private int fogRadius = 0;
    private boolean renderFog = false;

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

    @Override
    public List<KEntity> spatialSearchByIntersection(net.sf.jsi.Rectangle rect) {

        ArrayList<KEntity> results = new ArrayList<KEntity>();
        SaveToListProcedure myProc = new SaveToListProcedure();
        si.intersects(rect, myProc);
        for (int id : myProc.getIds()) {
            results.add(idToEntity.get(id));
        }
        return results;

    }

    public boolean isRenderFog() {
        return renderFog;
    }

    public void setRenderFog(boolean renderFog) {
        this.renderFog = renderFog;
    }

    public int getFogX() {
        return fogX;
    }

    public void setFogX(int fogX) {
        this.fogX = fogX;
    }

    public int getFogY() {
        return fogY;
    }

    public void setFogY(int fogY) {
        this.fogY = fogY;
    }

    public int getFogRadius() {
        return fogRadius;
    }

    public void setFogRadius(int fogRadius) {
        this.fogRadius = fogRadius;
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
            if (!isFogged(e)) {
                e.update();
            }
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

        return findEntitiesAtPoint(entities, x, y);

    }

    @Override
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
