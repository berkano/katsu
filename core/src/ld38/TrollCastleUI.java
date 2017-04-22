package ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import mini73.Console;

/**
 * Created by shaun on 22/04/2017.
 */
public class TrollCastleUI {

    public Console topBar = new Console();
    public Console bottomBar = new Console();

    public void start() {
        setupTopBar();
        setupBottomBar();
    }

    public void render() {
        topBar.render();
        bottomBar.render();
    }

    private void setupTopBar() {
        topBar.setShaded(true);
        topBar.setAutoResize(false);
        topBar.setBounds(0, Gdx.graphics.getHeight() - K.graphics.font.getLineHeight(), Gdx.graphics.getWidth(), K.graphics.font.getLineHeight());
        topBar.setAlignment(Align.topLeft);
    }

    private void setupBottomBar() {
        bottomBar.setShaded(true);
        bottomBar.setAutoResize(false);
        bottomBar.setBounds(0, 0, Gdx.graphics.getWidth(), K.graphics.font.getLineHeight());
        bottomBar.setAlignment(Align.topLeft);
    }


}
