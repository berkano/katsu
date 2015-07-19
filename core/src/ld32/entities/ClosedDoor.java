package ld32.entities;

import ld32.LD32Sounds;
import ld32.LevelBounds;
import katsu.Panko;
import katsu.PankoEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class ClosedDoor extends SolidEntity {

    private long lastLevelCompleteChecked = Panko.currentTime();

    private static ArrayList<LevelBounds> levelBoundses = new ArrayList<LevelBounds>();

    static {
        LevelBounds level1 = new LevelBounds();
        level1.doorX = 32; level1.doorY = 17;
        level1.topLeftX = 3; level1.topLeftY = 3;
        level1.bottomRightX = 33; level1.bottomRightY = 29;
        level1.level = 1;
        levelBoundses.add(level1);

        LevelBounds level2 = new LevelBounds();
        level2.doorX = 48; level2.doorY = 25;
        level2.topLeftX = 36; level2.topLeftY = 4;
        level2.bottomRightX = 62; level2.bottomRightY = 28;
        level2.level = 2;
        levelBoundses.add(level2);

        LevelBounds level3 = new LevelBounds();
        level3.doorX = 38; level3.doorY = 43;
        level3.topLeftX = 38; level3.topLeftY = 31;
        level3.bottomRightX = 62; level3.bottomRightY = 62;
        level3.level = 3;
        levelBoundses.add(level3);

        LevelBounds level4 = new LevelBounds();
        level4.doorX = 19; level4.doorY = 62;
        level4.topLeftX = 3; level4.topLeftY = 32;
        level4.bottomRightX = 35; level4.bottomRightY = 61;
        level4.level = 4;
        levelBoundses.add(level4);

        LevelBounds level5 = new LevelBounds();
        level5.doorX = 36; level5.doorY = 85;
        level5.topLeftX = 4; level5.topLeftY = 64;
        level5.bottomRightX = 35; level5.bottomRightY = 94;
        level5.level = 5;
        levelBoundses.add(level5);

        LevelBounds level6 = new LevelBounds();
        level6.doorX = 60; level6.doorY = 81;
        level6.topLeftX = 39; level6.topLeftY = 63;
        level6.bottomRightX = 61; level6.bottomRightY = 93;
        level6.level = 6;
        levelBoundses.add(level6);

        LevelBounds level7 = new LevelBounds();
        level7.doorX = 74; level7.doorY = 62;
        level7.topLeftX = 64; level7.topLeftY = 64;
        level7.bottomRightX = 92; level7.bottomRightY = 94;
        level7.level = 7;
        levelBoundses.add(level7);

        LevelBounds level8 = new LevelBounds();
        level8.doorX = 80; level8.doorY = 30;
        level8.topLeftX = 66; level8.topLeftY = 30;
        level8.bottomRightX = 93; level8.bottomRightY = 61;
        level8.level = 8;
        levelBoundses.add(level8);

        // Adjust for Tiled coord system
        for (LevelBounds b : levelBoundses) {
            b.bottomRightY = 99 - b.bottomRightY;
            b.topLeftY = 99 - b.topLeftY;
            b.doorY = 99 - b.doorY;
        }

    }


    @Override
    public void update() {
        super.update();
        checkLevelCompleted();
    }

    private void checkLevelCompleted() {
        if (lastLevelCompleteChecked > Panko.currentTime() - 3000) return;
        lastLevelCompleteChecked = Panko.currentTime();

        boolean levelComplete = false;

        // Which level am I?
        LevelBounds myLevel = null;

        for (LevelBounds b : levelBoundses) {
            if (b.doorX == getGridX() && b.doorY == getGridY()) myLevel = b;
        }

        if (myLevel == null) {
            Panko.getUI().writeText("err: closed door @ "+getGridX()+","+getGridY()+" could not determine level");
            return;
        }

        levelComplete = noEnemiesInBoundingBox(myLevel.topLeftX, myLevel.topLeftY, myLevel.bottomRightX, myLevel.bottomRightY);
        if (levelComplete && getHealth() >0) {
            setHealth(0);
            getRoom().getDeadEntities().add(this);
            OpenDoor openDoor = new OpenDoor();
            openDoor.setX(getX());
            openDoor.setY(getY());
            getRoom().getNewEntities().add(openDoor);
            openDoor.setRoom(getRoom());
            Panko.getUI().writeText("Level " + myLevel.level + "/8 complete! Door to next level is open.");
            LD32Sounds.complete_level.play();
        }

    }

    private boolean noEnemiesInBoundingBox(int topX, int topY, int bottomX, int bottomY) {
        // Careful since coords are flipped
        for (int x = topX; x <= bottomX; x++) {
            for (int y = bottomY; y <= topY; y++) {
                ArrayList<PankoEntity> entities = getRoom().findEntitiesAtPoint(x * getWidth(), y * getHeight());
                for (PankoEntity e : entities) {
                    if (e instanceof Spider) return false;
                }
            }
        }
        return true;
    }


    /*


    [Level 1]
32,17

3,3
33,29

[Level 2]
48,25

36, 4
62, 28

[Level 3]
38, 43

38, 31
62, 62

[L4]
19, 62

3, 32
35, 61

[L5]
36, 85

4, 64
35, 94

[L6]
60, 81

39, 63
61, 93

[L7]
74, 62

64, 64
92, 94

[L8]
80, 30

66, 30
93, 61





     */

}
