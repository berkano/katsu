package ld32.entities;

import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class ClosedDoor extends SolidEntity {

    private long lastLevelCompleteChecked = Panko.currentTime();


    @Override
    public void update() {
        super.update();
        checkLevelCompleted();
    }

    private void checkLevelCompleted() {
        if (lastLevelCompleteChecked > Panko.currentTime() - 1000) return;
        lastLevelCompleteChecked = Panko.currentTime();

        boolean levelComplete = false;

        // Which level am I?
        int myLevel = 0;

        int topX = 0;
        int topY = 0;
        int bottomX = 0;
        int bottomY = 0;

        if (getGridX() == 15 && getGridY() == 99-19) myLevel = 1;

        if (myLevel == 1) {
            topX = 0; topY = 99-5;
            bottomX = 15; bottomY=99-24;
        }

        if (myLevel == 0) {
            Panko.getUI().writeText("err: closed door @ "+getGridX()+","+getGridY()+" could not determine level");
        }

        levelComplete = true;
        if (levelComplete && getHealth() >0) {
            setHealth(0);
            getRoom().getDeadEntities().add(this);
            OpenDoor openDoor = new OpenDoor();
            openDoor.setX(getX());
            openDoor.setY(getY());
            getRoom().getNewEntities().add(openDoor);
            openDoor.setRoom(getRoom());
            Panko.getUI().writeText("Level "+myLevel + "complete!");
        }

    }
}
