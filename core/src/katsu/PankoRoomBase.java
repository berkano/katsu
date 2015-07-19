package katsu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class PankoRoomBase implements PankoRoom, InputProcessor {

    protected ArrayList<PankoEntity> entities;
    protected ArrayList<PankoEntity> newEntities;
    protected ArrayList<PankoEntity> onTopQueue;
    protected ArrayList<PankoEntity> deadEntityQueue;
    private boolean active;

    public void bringEntityToFront(PankoEntity entity) {
        entities.remove(entity);
        entities.add(entity);

    }

    public void createInstancesAtAll(Class find, Class toAdd) {
        for (PankoEntity e : entities) {
            if (find.isInstance(e)) {
                try {
                    PankoEntity newEntity = (PankoEntity)toAdd.newInstance();
                    newEntity.setX(e.getX());
                    newEntity.setY(e.getY());
                    newEntities.add(newEntity);
                } catch (Exception ex) {

                }

            }
        }
    }

    public void bringAllInstancesToFront(Class clazz) {
        for (PankoEntity e : entities) {
            if (clazz.isInstance(e)) {
                onTopQueue.add(e);
            }
        }
    }

    public void sendAllInstancesToBack(Class clazz) {
        for (PankoEntity e : entities) {
            if (!clazz.isInstance(e)) {
                onTopQueue.add(e);
            }
        }
    }


    public PankoEntity firstInstanceOfClass(Class clazz) {
        for (PankoEntity e : entities) {
            if (clazz.isInstance(e)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void start() {
        setActive(true);
        Panko.getInputMultiplexer().addProcessor(this);
        entities = new ArrayList<PankoEntity>();
        newEntities = new ArrayList<PankoEntity>();
        onTopQueue = new ArrayList<PankoEntity>();
        deadEntityQueue = new ArrayList<PankoEntity>();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void render() {

        for (PankoEntity e : deadEntityQueue) {
            entities.remove(e);
            onTopQueue.remove(e);
            newEntities.remove(e);
        }

        deadEntityQueue.clear();

        for (PankoEntity e : newEntities) {
            entities.add(e);
        }

        newEntities.clear();

        for (PankoEntity e : onTopQueue) {
            entities.remove(e);
            entities.add(e);
        }

        onTopQueue.clear();
        
        for (PankoEntity e : entities) {
            e.render();
        }
    }

    @Override
    public InputProcessor getInputProcessor() {
        return this;
    }

    @Override
    public void setEntities(ArrayList<PankoEntity> entities) {
        this.entities = entities;
    }

    @Override
    public ArrayList<PankoEntity> getEntities() {
        return entities;
    }

    @Override
    public void update() {
        for (PankoEntity e : entities) {
            e.update();
        }
    }

    @Override
    public List getNewEntities() {
        return newEntities;
    }

    @Override
    public List getOnTopQueue() {
        return onTopQueue;
    }

    @Override
    public ArrayList<PankoEntity> getDeadEntities() {
        return deadEntityQueue;
    }

    @Override
    public ArrayList<PankoEntity> findEntitiesAtPoint(int x, int y) {

            ArrayList<PankoEntity> result = new ArrayList<PankoEntity>();

            for (PankoEntity e : entities) {

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
