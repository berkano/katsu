package ld33;

import katsu.*;
import ld33.entities.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD33Game extends KRunner {

    @Override
    public ArrayList<KRoom> getRooms() {

        ArrayList<KRoom> rooms = new ArrayList<KRoom>();
        rooms.add(new World());
        return rooms;

    }

    @Override
    public void resize(int width, int height) {

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
    public String getResourceRoot() {
        return "ld33";
    }

    @Override
    public List<Class> getClassLookup() {

        return K.utils.buildClassList(
                Monster.class, Wall.class, Floor.class, Warrior.class, Dwarf.class,
                Violet.class, Door.class, Carpet.class, Wall2.class, Water.class, Grass.class,
                Sheep.class, Cat.class, StairsUp.class, StairsDown.class, Bed.class, Blood.class,
                WaterSlick.class, OilSlick.class, Tree.class, Bar.class, Skeleton.class

        );

    }

    @Override
    public void toggleMusic() {
        LD33Sounds.toggleMusic();
    }

}
