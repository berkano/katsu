package panko.components;

import panko.components.Panko;
import panko.components.PankoTmxData;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoTmxHelper {

    public static PankoTmxData dataFromMap(String tmxName) {
        return new PankoTmxData(tmxName, Panko.getImpl().getClassLookup()).loadFromMap();
    }

    public static ArrayList<PankoEntity> entitiesFromMap(String tmxName) {
        return dataFromMap(tmxName).getEntities();
    }

}
