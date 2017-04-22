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
    public Console help = new Console();

    public void start() {
        setupTopBar();
        setupBottomBar();
        bottomBar.writeLine("[GREEN]A beautiful day in Trog.");
        setupHelp();
        toggleHelp();
    }

    public void render() {
        topBar.render();
        help.render();
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
        bottomBar.setLineLimit(4);
        bottomBar.setBounds(0, 0, Gdx.graphics.getWidth(), 4 * K.graphics.font.getLineHeight());
        bottomBar.setAlignment(Align.topLeft);
    }

    private void setupHelp() {
        help.setShaded(true);
        help.setAutoResize(false);
        help.setVisible(false);
        help.setBounds(64, 128 + 32, Gdx.graphics.getWidth() - 128, 13 * K.graphics.font.getLineHeight());
        help.setAlignment(Align.center);

        help.writeLine("[RED]~[ORANGE]~[YELLOW]~ [WHITE]Welcome to [CYAN]Troll Castle[WHITE]! [YELLOW]~[ORANGE]~[RED]~");
        help.writeLine("");
        help.writeLine("[CYAN]INSTRUCTIONS[WHITE]. Click a troll to get its attention.");
        help.writeLine("Pressing [GREEN]G[WHITE] tells it to [GREEN]G[WHITE]o somewhere.");
        help.writeLine("[GREEN]Click[WHITE] where you want it to go.");
        help.writeLine("When a troll is standing on top of");
        help.writeLine("something, press [GREEN]Space[WHITE] to carry out the");
        help.writeLine("related action (Mine, Eat, etc).");
        help.writeLine("");
        help.writeLine("Press [GREEN]H[WHITE] to hide or show this help screen.");



    }


    public void toggleHelp() {

        if (help.isVisible()) {
            help.setVisible(false);
            bottomBar.setVisible(true);
            topBar.setVisible(true);
        } else {
            help.setVisible(true);
            topBar.setVisible(false);
            bottomBar.setVisible(false);
        }


    }
}
