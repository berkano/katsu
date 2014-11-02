package ld28;

import katsu.Util;

/**
 * Created by shaun on 15/12/13.
 */
public class Documentation {

    public static String documentationAsText = Util.loadTextResourceAsString("help.txt");

    public static String[] documentationLines = documentationAsText.split("\n");
    public static int linesPerPage = 9;

    public static String getHelpPage(int pageNum) {
        return getHelpLines((pageNum - 1)*linesPerPage, pageNum * linesPerPage);
    }

    public static int getNumHelpPages() {
        return documentationLines.length / linesPerPage;
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
