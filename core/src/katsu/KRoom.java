package katsu;

import com.badlogic.gdx.InputProcessor;
import ld33.entities.MobBase;
import net.sf.jsi.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 15/11/2014.
 */
public interface KRoom {

    public void start();

    public boolean isActive();

    void render();

    InputProcessor getInputProcessor();

    public void setEntities(ArrayList<KEntity> entities);

    public ArrayList<KEntity> getEntities();

    void update();

    List getNewEntities();

    ArrayList<KEntity> findEntitiesAtPoint(int x, int y);

    ArrayList<KEntity> findEntitiesAtPoint(List<KEntity> entities, int x, int y);

    void addNewEntity(KEntity entity);

    void updateSpatialMap(KEntity entity);

    List<KEntity> spatialSearchByIntersection(Rectangle rectangle);


}
