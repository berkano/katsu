package katsu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ext.pathfinding.grid.GridMap;
import ld28.LevelManager;
import ld28.Sounds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Input.Buttons;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 23/03/13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public abstract class Room {

    public int width = 768;
    public int height = 768;

    public Game game = Util.getGame();
    public Sounds sounds = Util.getSounds();
    public Random random = Util.getRandom();
    public UI ui = Util.getUI();

    public List<View> views = new ArrayList<View>();
    public List<Entity> entities = new ArrayList<Entity>();
    public List<Entity> destroyList = new ArrayList<Entity>();
    public List<Entity> createList = new ArrayList<Entity>();

    public HashMap<String, Class> classLookup = LevelManager.getTmxClassMapping();
    public HashMap<Class, TextureRegion> entityTextureRegions = new HashMap<Class, TextureRegion>();

    public long tick = 0;

    public int speedFactor = 1;
    public Color lightColor;

    int pathMapSizeX = 64;
    int pathMapSizeY = 64;

    public GridMap pathMap = new GridMap(pathMapSizeX, pathMapSizeY);

    public void render(Graphics g, Application gc, SpriteBatch batch) {

        Logger.trace("Room.render()");

        for (View v : views) {
            v.render(g, batch);
        }

    }

    public void addEntity(Entity entity) {
        entity.textureRegion = entityTextureRegions.get(entity.getClass());
        entity.room = this;

        // insert at beginning, so UI sprites are always on top, eep
        entities.add(0, entity);
    }


    public void update(Application gc) {

        boolean leftClicking = false;
        if (Katsu.game.isButtonPressed(Buttons.LEFT)) leftClicking = true;
        boolean rightClicking = false;
        if (Katsu.game.isButtonPressed(Buttons.RIGHT)) rightClicking = true;

        if (leftClicking || rightClicking) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            // TODO-LD28 hack
            if (y > 16) {
                Coord roomCoord = screenCoordsToRoomCoords(x, y);
                if (roomCoord != null) {
                    onClick(roomCoord.x, roomCoord.y, leftClicking, rightClicking);
                }
            } else {
                rawScreenClick(x, y);
            }
        }

        if (game.paused) return;

        for (View v : views) {
            v.update(gc);
        }

        // Update collision map for pathfinder
        //pathMap = new GridMap(pathMapSizeX, pathMapSizeY); // TODO base this on size of tiled map

        ArrayList<Entity> interestedInTargetCollision = new ArrayList<Entity>();
        for (Entity e : entities) {
            if (e.isCollisionTarget) {
                interestedInTargetCollision.add(e);
            }
        }

        for (Entity e : entities) {
            e.update(gc);

            if (e.wantsMove) {

                boolean collision = false;

                tick++;

                if (e.solid) {

                    for (Entity e2 : interestedInTargetCollision) {
                        if ((e2.solid)) {
                            if (e == e2) continue;
                            if (e.newX < e2.x - Settings.tileWidth * 2) continue;
                            if (e.newY < e2.y - Settings.tileHeight * 2) continue;
                            if (e.newX > e2.x + Settings.tileWidth * 2) continue;
                            if (e.newY > e2.y + Settings.tileHeight * 2) continue;

                            Rectangle r1 = new Rectangle(e.newX, e.newY, Settings.tileWidth, Settings.tileHeight);
                            Rectangle r2 = new Rectangle(e2.x, e2.y, Settings.tileWidth, Settings.tileHeight);

                            boolean oldCollision = collision;

                            if (r1.overlaps(r2)) {
                                if (!e.wantsDestroy && !e2.wantsDestroy) {

                                    if (e.collide(e2)) {
                                        collision = true;
                                        if (Settings.collisionDebug)
                                            ui.writeText("Collided " + e.getClass() + " with " + e2.getClass());

                                    }
                                    if (e2.collide(e)) {
                                        collision = true;
                                        if (Settings.collisionDebug)
                                            ui.writeText("Collided " + e.getClass() + " with " + e2.getClass());
                                    }
                                }

                            }

                        }
                    }
                }

                if (!collision) {

                    if (e.orientSpriteByMovement) {
                        if (e.newY < e.y) {
                            e.orientation = KatsuOrientation.UP;
                        }
                        if (e.newY > e.y) {
                            e.orientation = KatsuOrientation.DOWN;
                        }
                        if (e.newX < e.x) {
                            e.orientation = KatsuOrientation.LEFT;
                        }
                        if (e.newX > e.x) {
                            e.orientation = KatsuOrientation.RIGHT;
                        }
                    }


                    e.x = e.newX;
                    e.y = e.newY;
                    e.afterMoved();
                    e.lastMoved = System.currentTimeMillis();
                }
                e.wantsMove = false;
            }
        }

        for (Entity e : entities) {
            if (e.wantsDestroy) {
                destroyList.add(e);
            }
        }

        for (Entity e : destroyList) {
            e.beforeDeath(this);
            entities.remove(e);
        }
        destroyList.clear();

        for (Entity e : createList) {
            e.textureRegion = entityTextureRegions.get(e.getClass());
            entities.add(e);
        }
        createList.clear();
    }

    protected abstract void rawScreenClick(int x, int y);

    public void onClick(int roomX, int roomY, boolean leftClicking, boolean rightClicking) {
        ArrayList<Entity> clickedEntities = findEntitiesAtPoint(roomX, roomY);

        if (clickedEntities.size() >= 1) {
            if (leftClicking) {
                ui.writeText(clickedEntities.get(clickedEntities.size() - 1).toString());
            }
        }

    }

    public int entityCount(Class c) {
        int count = 0;
        for (Entity e : entities) {
            if (c.isInstance(e)) {
                count++;
            }
        }
        return count;

    }

    public Entity findFirst(Class c) {
        for (Entity e : entities) {
            if (c.isInstance(e)) {
                return e;
            }
        }
        return null;
    }

    public Entity findNearest(int x, int y, Class c) {
        return findNearest(x, y, c, 9999999, null);
    }

    public Entity findNearest(int x, int y, Class c, int maxDistance, Class exclude) {

        Object found = null;
        int distance = 9999999;

        for (Entity e : entities) {

            if (exclude != null) {
                if (exclude.isInstance(e)) continue;
            }

            if (c.isInstance(e)) {
                int dx = e.x - x;
                int dy = e.y - y;
                int d = (int) Math.round(Math.sqrt(dx * dx + dy * dy));
                if (d < distance && d < maxDistance) {
                    found = e;
                    distance = d;
                }
            }
        }
        return (Entity) found;
    }

    public void bringEntitiesToFront(Class filterClass) {

        ArrayList<Entity> onTopEntities = new ArrayList<Entity>();

        // Sort components to always be on top
        for (Entity e : entities) {
            if (filterClass.isInstance(e)) onTopEntities.add(e);
        }

        for (Entity e : onTopEntities) {
            entities.remove(e);
            entities.add(e);
        }

    }


    public ArrayList<Entity> findEntitiesAtPoint(int x, int y) {

        ArrayList<Entity> result = new ArrayList<Entity>();

        for (Entity e : entities) {

            // Quick optimization
            if (e.x > x) continue;
            if (e.y > y) continue;
            if (e.x < x - e.width) continue;
            if (e.y < y - e.height) continue;

            Rectangle r = new Rectangle(e.x, e.y, e.width, e.height);

            if (r.contains(x, y)) {
                result.add(e);
            }

        }
        return result;
    }


    public Coord screenCoordsToRoomCoords(int x, int y) {

        Coord result = new Coord();

        for (View v : views) {
            if ((x >= v.screenX) && (x <= v.screenX + v.screenWidth)) {
                if ((y >= v.screenY) && (y <= v.screenY + v.screenHeight)) {

                    // X Y pixel position within view
                    double viewX = x - v.screenX;
                    double viewY = y - v.screenY;

                    // Current view scale relative to room
                    double viewXScale = (double) v.screenWidth / (double) v.portWidth;
                    double viewYScale = (double) v.screenHeight / (double) v.portHeight;

                    // X Y room position within view
                    int roomX = (int) Math.round((viewX / viewXScale + (double) v.portX)); // - Settings.tileWidth / 2;  // Aiming for centre of tile
                    int roomY = (int) Math.round((viewY / viewYScale + (double) v.portY)); // - Settings.tileHeight / 2;

                    result.x = roomX;
                    result.y = roomY;

                    return result;

                }
            }
        }

        return null;

    }

    public Entity nearestOf(Entity from, Entity... entities) {

        Object found = null;
        int distance = 9999999;

        for (Entity e : entities) {
            if (e == null) continue;

            int dx = e.x - from.x;
            int dy = e.y - from.y;
            int d = (int) Math.round(Math.sqrt(dx * dx + dy * dy));
            if (d < distance) {
                found = e;
                distance = d;
            }
        }
        return (Entity) found;
    }

    public abstract void preRender();
}
