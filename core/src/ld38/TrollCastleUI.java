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
        help.setBounds(48, 64, Gdx.graphics.getWidth() - 96, 18 * K.graphics.font.getLineHeight());
        help.setAlignment(Align.center);

        help.writeLine("[RED]~[ORANGE]~[YELLOW]~ [WHITE]Welcome to [CYAN]Troll Castle[WHITE]! [YELLOW]~[ORANGE]~[RED]~");
        help.writeLine("");
        help.writeLine("[CYAN]INSTRUCTIONS[WHITE]. [GREEN]Click[WHITE] a troll to select it.");
        help.writeLine("[GREEN]Click[WHITE] where you want it to go. You can drag the map");
        help.writeLine("with the mouse. [GREEN]Click[WHITE] a troll again to deselect it.");
        help.writeLine("When no troll is selected, you can [GREEN]click things[WHITE] for information.");
        help.writeLine("When a troll is standing on top of something, press");
        help.writeLine("[GREEN]SPACE[WHITE] to carry out the");
        help.writeLine("related action (Mine, Fish, Build, etc).");
        help.writeLine("");
        help.writeLine("The object of the game is to build a [CYAN]Troll Castle[WHITE] to cover the");
        help.writeLine("yellow square. First build the walls, then a tower on all 4");
        help.writeLine("corners, then upgrade each one to a [YELLOW]Gold Tower[WHITE]. Build by moving");
        help.writeLine("a troll to the right location and pressing [GREEN]SPACE[WHITE]. The Trolls will");
        help.writeLine("give you a lot of help if you can translate their language!");
        help.writeLine("");
        help.writeLine("Press [GREEN]H[WHITE] to hide or show this help screen. Have fun!");



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
