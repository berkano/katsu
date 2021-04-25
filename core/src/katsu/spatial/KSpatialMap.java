package katsu.spatial;

import gnu.trove.procedure.TIntProcedure;
import katsu.model.KEntity;
import net.sf.jsi.SpatialIndex;
import net.sf.jsi.rtree.RTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 30/07/2016.
 */
public class KSpatialMap {

    // Spatial indexing of entities
    private SpatialIndex si;
    private int lastID;
    private HashMap<Integer, KEntity> idToEntity;
    private HashMap<Integer, net.sf.jsi.Rectangle> idToRectangle;
    private HashMap<KEntity, Integer> entityToID;

    public void handleEntityDelete(KEntity e) {

        Integer id = entityToID.get(e);
        // Cleanup spatial index
        if (id != null) {
            si.delete(idToRectangle.get(id), id);
            idToRectangle.remove(id);
            idToEntity.remove(id);
            entityToID.remove(e);
        }


    }

    class SaveToListProcedure implements TIntProcedure {
        private List<Integer> ids = new ArrayList<Integer>();

        public boolean execute(int id) {
            ids.add(id);
            return true;
        }

        private List<Integer> getIds() {
            return ids;
        }
    }



    public void wipeData() {

        idToEntity = new HashMap<Integer, KEntity>();
        idToRectangle = new HashMap<Integer, net.sf.jsi.Rectangle>();
        entityToID = new HashMap<KEntity, Integer>();
        si = new RTree();
        si.init(null);
        lastID = 0;

    }

    public void update(KEntity entity) {

        // Remove any existing entries in the index
        Integer id = entityToID.get(entity);
        if (id != null) {
            si.delete(idToRectangle.get(id), id);
        }

        // Create rectangle
        net.sf.jsi.Rectangle rect = new net.sf.jsi.Rectangle(
                entity.getX() + entity.getLeftMargin(),entity.getY() + entity.getTopMargin(),
                entity.getX() + entity.getWidth() - 1 - entity.getRightMargin(), entity.getY() + entity.getHeight() - 1 - entity.getBottomMargin());

        id = lastID;
        lastID++;

        // Add to spatial index
        si.add(rect, id);

        // Keep track of rectangles and entities for later
        idToEntity.put(id, entity);
        idToRectangle.put(id, rect);
        entityToID.put(entity, id);

    }

    public List<KEntity> searchByIntersection(net.sf.jsi.Rectangle rect) {

        ArrayList<KEntity> results = new ArrayList<KEntity>();
        SaveToListProcedure myProc = new SaveToListProcedure();
        si.intersects(rect, myProc);
        for (int id : myProc.getIds()) {
            results.add(idToEntity.get(id));
        }
        return results;
    }

}
