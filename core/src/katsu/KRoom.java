package katsu;

import com.badlogic.gdx.InputProcessor;

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

    ArrayList<KEntity> getDeadEntities();

    ArrayList<KEntity> findEntitiesAtPoint(int x, int y);

}
