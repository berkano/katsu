package katsu.ld48;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.graphics.KConsole;

/**
 * Created by shaun on 22/04/2017.
 */
public class LD48UI {

    KConsole topBar = new KConsole();
    public KConsole bottomBar = new KConsole();
    private KConsole help = new KConsole();

    public void start() {
        setupTopBar();
        setupBottomBar();
        bottomBar.writeLine("[GREEN]A beautiful day in Trog.");
        setupHelp();
        toggleHelp();
    }

    void render() {
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
        bottomBar.setLineLimit(8);
        bottomBar.setBounds(0, 0, Gdx.graphics.getWidth(), 8 * K.graphics.font.getLineHeight());
        bottomBar.setAlignment(Align.topLeft);
    }

    private void setupHelp() {
        help.setShaded(true);
        help.setAutoResize(false);
        help.setVisible(false);

        String[] helpText = K.resource.loadText("help.txt").split("\\r?\\n");

        for (String line : helpText) {
            help.writeLine(line);
        }

        help.setBounds(128, 16, Gdx.graphics.getWidth() - 256, help.size() * K.graphics.font.getLineHeight());
        help.setAlignment(Align.topLeft);


    }


    void toggleHelp() {

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
