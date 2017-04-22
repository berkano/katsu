package ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import mini73.Console;

/**
 * Created by shaun on 22/04/2017.
 */
public class UI {

    public Console topBar = new Console();

    public void start() {
        setupTopBar();
    }

    public void render() {
        topBar.render();
    }

    private void setupTopBar() {
        //topBar.setAutoResize(false);
        //topBar.setBounds(0, Gdx.graphics.getHeight(), Gdx.graphics.getWidth(), 2 * K.graphics.font.getLineHeight());
        //topBar.setAlignment(Align.topLeft);
    }

}
