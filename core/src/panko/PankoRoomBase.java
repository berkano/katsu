package panko;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public abstract class PankoRoomBase implements PankoRoom {

    protected ArrayList<PankoEntity> entities;
    private boolean active;

    @Override
    public void start() {
        setActive(true);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void render() {
        for (PankoEntity e : entities) {
            e.render();
        }
    }
}
