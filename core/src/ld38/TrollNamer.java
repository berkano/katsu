package ld38;

import katsu.K;

import java.util.ArrayList;

/**
 * Created by shaun on 22/04/2017.
 */
public class TrollNamer {

    private static TrollNamer _instance;

    private ArrayList<String> availableNames = new ArrayList<String>();

    public TrollNamer() {
        generateNames();
    }

    private void generateNames() {

        availableNames.add("Bob1");
        availableNames.add("Bob2");
        availableNames.add("Bob3");
        availableNames.add("Bob4");
        availableNames.add("Bob5");
        availableNames.add("Bob6");
        availableNames.add("Bob7");
        availableNames.add("Bob8");
        availableNames.add("Bob9");

    }

    public static TrollNamer instance() {
        if (_instance == null) {
            _instance = new TrollNamer();
        }
        return _instance;
    }

    public String nextName() {
        int pick = K.random.nextInt(availableNames.size());
        String result = availableNames.get(pick);
        availableNames.remove(pick);
        return result;
    }
}
