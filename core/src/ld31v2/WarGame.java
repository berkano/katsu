package ld31v2;

import com.badlogic.gdx.audio.Sound;
import ld31v2.entities.*;
import panko.PankoGame;
import panko.PankoResource;
import panko.PankoRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class WarGame implements PankoGame {

    public static Sound vox1;
    public static Sound vox2;
    public static Sound spawn;
    public static Sound death;
    public static Sound soundtrack;
    public static Sound killed_enemy;

    public static boolean musicPlaying = false;

    public void loadSounds() {
        vox1 = PankoResource.loadSound("vox1.wav");
        vox2 = PankoResource.loadSound("vox2.wav");
        spawn = PankoResource.loadSound("spawn.wav");
        death = PankoResource.loadSound("death.wav");
        soundtrack = PankoResource.loadSound("soundtrack.mp3");
        killed_enemy = PankoResource.loadSound("killed_enemy.wav");
    }

    private static HashMap<String, Class> classLookup = new HashMap<String, Class>();

    private static CampaignMap campaignMap;

    public static CampaignMap getRoom() {
        return campaignMap;
    }

    static {
        classLookup.put("Grass", Grass.class);
        classLookup.put("Dirt", Dirt.class);
        classLookup.put("Water", Water.class);
        classLookup.put("Tower", Tower.class);
        classLookup.put("WallHorz", WallHorz.class);
        classLookup.put("WallVert", WallVert.class);
        classLookup.put("SoldierP1", SoldierP1.class);
        classLookup.put("SoldierP2", SoldierP2.class);
        classLookup.put("SoldierP3", SoldierP3.class);
        classLookup.put("Hills", Hills.class);
        classLookup.put("Mountains", Mountains.class);
        classLookup.put("Selection", Selection.class);
        classLookup.put("Spawner", Spawner.class);
        classLookup.put("Skeleton", Skeleton.class);
    }

    public WarGame() {
    }

    @Override
    public ArrayList<PankoRoom> getRooms() {

        loadSounds();
        toggleMusic();

        campaignMap = new CampaignMap();

        return new ArrayList<PankoRoom>(
                Arrays.asList(
                        campaignMap
                )
        );
    }

    @Override
    public String getResourceRoot() {
        return "ld31v2";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return classLookup;
    }

    @Override
    public void toggleMusic() {
        if (musicPlaying) {
            soundtrack.stop();
            musicPlaying = false;
        } else {
            soundtrack.loop();
            musicPlaying = true;
        }
    }
}
