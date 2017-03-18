package mini73;

/**
 * Created by shaun on 15/12/13.
 */
public class Documentation {

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
            "Z: toggle zoom level";

    public static String getHelpPage(int pageNum) {
        if (pageNum == 1) return getHelpLines(1, 5);
        if (pageNum == 2) return getHelpLines(6, 10);
        if (pageNum == 3) return getHelpLines(11, 13);
        return "error";
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
