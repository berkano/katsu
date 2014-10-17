package ld28;

import katsu.Util;

/**
 * Created by shaun on 15/12/13.
 */
public class Documentation {

    /*
    public static String documentationAsText = "WASD: move person or ship around\n" +
            "U: switch between Ship and Person view\n" +
            "Esc: quit game\n" +
            "F / F11: toggle full screen\n" +
            "M: toggle music\n" +
            "H: display this help\n" +
            "1..9: buy from selected trade\n" +
            "1..9: sell inventory to person (doesn't matter which number)\n" +
            "P: pause game\n" +
            "T: teleport\n" +
            "I: pickup item into inventory or drop from inventory\n" +
            "SPACE: use selected item\n" +
            "Z: toggle zoom level";*/

    public static String documentationAsText = Util.loadTextResourceAsString("help.txt");

    public static String[] documentationLines = documentationAsText.split("\n");
    public static int linesPerPage = 9;
    public static int numPages = documentationLines.length / linesPerPage;

    public static String getHelpPage(int pageNum) {
        return getHelpLines((pageNum - 1)*linesPerPage, pageNum * linesPerPage);
    }

    private static String getHelpLines(int from, int to) {
        String result = "";
        String[] lines = documentationAsText.split("\n");
        for (int i = 1; i <= lines.length; i++) {
            if (i >= from && i <=to){
                result += lines[i-1]+"\n";
            }

        }
        return result;
    }


}
