package katsu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import lombok.Getter;
import lombok.Setter;
import mini73.UnportedCodeException;

import java.util.HashMap;

/**
 * Created by shaun on 27/02/2016.
 */
public class KInput {

    @Getter @Setter private InputMultiplexer multiplexer = new InputMultiplexer();

    private HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();

    public void setKeyDown(int keycode, boolean isDown) {

        keysDown.remove(keycode);

        if (isDown) {
            keysDown.put(keycode, true);
        }
    }

    public boolean isKeyDown(int keycode) {
        return keysDown.get(keycode) != null;
    }

    public void init(KRunner runner) {

        Gdx.input.setInputProcessor(K.input.getMultiplexer());
        getMultiplexer().addProcessor(runner);

    }

    public boolean isKeyTyped(int k) {
        throw new UnportedCodeException();
    }
}
