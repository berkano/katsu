package katsu;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ld28.Sounds;
import ld28.rooms.MainRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Game implements ApplicationListener, InputProcessor {

    private FPSLogger fpsLogger = new FPSLogger();

    public static long tick = 0;
    public static long frame = 0;
    public static long pathFinds = 0;

    public String pinCode = "";
    public String currentLevel = "";
    public List<Room> rooms = new ArrayList<Room>();
    public Room currentRoom;
    public Graphics.DisplayMode initialDisplayMode;

    public HashMap<Integer, Boolean> keysTyped = new HashMap<Integer, Boolean>();
    public HashMap<Integer, Boolean> keysDown = new HashMap<Integer, Boolean>();
    public HashMap<Integer, Boolean> buttonsPressed = new HashMap<Integer, Boolean>();

    public static Game instance = null;
    public static SpriteBatch spriteBatch;
    public static SpriteBatch uiSpriteBatch;
    public static ShapeRenderer shapeRenderer;
    public static ShapeRenderer uiShapeRenderer;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public static OrthographicCamera camera;
    private Sound sound;
    private Music music;
    BitmapFont font;
    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";

    private int playerX = 0;
    private int playerY = 0;

    private boolean doneSoundLoop = false;

    public boolean paused = false;
    public Random r = new Random();
    public Sounds sounds;
    public UI ui = new UI();
    public boolean musicEnabled;

    public int helpPage = 1;
    public int tutorialPage = 1;

    private KatsuGame impl;
    private LevelManager levelManager;

    public Game(KatsuGame impl) {
        this.impl = impl;
    }

    @Override
    public void create() {

        // Camera & GFX
        initialDisplayMode = Gdx.graphics.getDesktopDisplayMode();
        Gdx.graphics.setTitle(Settings.gameName + " :: " + Settings.gameAuthor + " :: " + Settings.gameDescription);
        instance = this;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        uiShapeRenderer = new ShapeRenderer();
        uiShapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        uiSpriteBatch = new SpriteBatch();
        uiSpriteBatch.setProjectionMatrix(camera.combined);
        Gdx.input.setInputProcessor(this);
        Logger.info("Game.create()");
        sounds = new Sounds();
        if (Settings.startWithMusic) {
            musicEnabled = true;
            //Util.loopMusic(sounds.music);
        }
        startLevel(Settings.startLevel);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void render() {
        frame++;
        int framesPerTick = Settings.targetFrameRate / Settings.ticksPerSecond;
        if (frame % framesPerTick == 0) {
            tick++;
            currentRoom.update(Gdx.app);
        }
        if (Settings.showFPS) fpsLogger.log();
        processInput();

        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentRoom.preRender();

        spriteBatch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        uiShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        currentRoom.render(Gdx.graphics, Gdx.app, spriteBatch);
        spriteBatch.end();
        shapeRenderer.end();

        // enable blending for shadow box behind text
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ui.renderShadowBox();
        uiShapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        uiSpriteBatch.begin();
        ui.render(Gdx.graphics, Gdx.app, uiSpriteBatch);
        uiSpriteBatch.end();



    }

    public static Game getInstance() {
        return instance;
    }

    public boolean isButtonPressed(int button) {
        if (buttonsPressed.get(button) != null) {
            if (buttonsPressed.get(button) == true) {
                buttonsPressed.remove(button);
                buttonsPressed.put(button, false);
                return true;
            }
        }
        return false;
    }

    private void processInput() {

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            if (buttonsPressed.get(Buttons.LEFT) == null) {
                buttonsPressed.put(Buttons.LEFT, true);
            }
        } else {
            buttonsPressed.remove(Buttons.LEFT);
        }

        if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
            if (buttonsPressed.get(Buttons.RIGHT) == null) {
                buttonsPressed.put(Buttons.RIGHT, true);
            }
        } else {
            buttonsPressed.remove(Buttons.RIGHT);
        }

        if (Katsu.game.isKeyTyped(Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Katsu.game.isKeyTyped(Keys.F) || Katsu.game.isKeyTyped(Keys.F11)) {
            Util.toggleFullScreenMode();
        }

        if (Katsu.game.isKeyTyped(Keys.M)) {

            if (musicEnabled) {
                Sounds.stopAllMusic();
            }
            musicEnabled = !musicEnabled;

        }

        if (Katsu.game.isKeyTyped(Keys.H)) {
            doHelp();

        }

        if (Settings.pinCodesEnabled) handlePinCodes();
        if (Katsu.game.isKeyTyped(Keys.P)) {
            paused = !paused;
        }

    }

    private void handlePinCodes() {
        if (Katsu.game.isKeyTyped(Keys.NUM_0)) {
            pinCode += "0";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_1)) {
            pinCode += "1";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_2)) {
            pinCode += "2";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_3)) {
            pinCode += "3";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_4)) {
            pinCode += "4";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_5)) {
            pinCode += "5";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_6)) {
            pinCode += "6";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_7)) {
            pinCode += "7";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_8)) {
            pinCode += "8";
        }
        if (Katsu.game.isKeyTyped(Keys.NUM_9)) {
            pinCode += "9";
        }

        if (pinCode.length() > 4) {
            pinCode = pinCode.substring(1, 5);
        }

        startLevel(pinCode);
    }

    public void doHelp() {
        ui.text.clear();
        getLevelManager().showHelp(helpPage, ui);

        helpPage++;
        if (helpPage > impl.getNumHelpPages()) helpPage = 1;

    }

    public void startLevel(String code) {
        String tmx = getLevelManager().tmxForLevelCode(code);
        if (tmx.equals("")) return;
        pinCode = "";
        currentLevel = code;
        ui.text.clear();
        rooms.clear();
        rooms.add(new MainRoom(tmx));
        currentRoom = null;
        currentRoom = rooms.get(0);

        getLevelManager().showLevelInstructions(ui, this);

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        keysTyped.remove(keycode);
        keysTyped.put(keycode, true);
        keysDown.remove(keycode);
        keysDown.put(keycode, true);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyUp(int keycode) {
        keysDown.remove(keycode);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean keyTyped(char character) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isKeyTyped(int keycode) {
        if (keysTyped.containsKey(keycode)) {
            keysTyped.remove(keycode);
            return true;
        }
        return false;
    }

    public boolean isKeyDown(int keycode) {
        if (keysDown.containsKey(keycode)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean scrolled(int amount) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public LevelManager getLevelManager() {
        return impl.getLevelManager();
    }
}
