package katsu;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class KRoomBase implements KRoom, InputProcessor {

    protected ArrayList<KEntity> entities;
    protected ArrayList<KEntity> newEntities;
    protected ArrayList<KEntity> deadEntityQueue;
    private boolean active;

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
        deadEntityQueue = new ArrayList<KEntity>();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void render() {

        for (KEntity e : deadEntityQueue) {
            entities.remove(e);
            newEntities.remove(e);
        }

        deadEntityQueue.clear();

        for (KEntity e : newEntities) {
            entities.add(e);
        }

        newEntities.clear();

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

    @Override
    public void update() {
        for (KEntity e : entities) {
            e.update();
        }
    }

    @Override
    public List getNewEntities() {
        return newEntities;
    }

    @Override
    public ArrayList<KEntity> getDeadEntities() {
        return deadEntityQueue;
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
