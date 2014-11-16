package panko;

import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;

/**
 * Created by shaun on 15/11/2014.
 */
public interface PankoRoom {

    public void start();

    public boolean isActive();

    void render();

    InputProcessor getInputProcessor();

    public void setEntities(ArrayList<PankoEntity> entities);

    public ArrayList<PankoEntity> getEntities();

    void update();
}
