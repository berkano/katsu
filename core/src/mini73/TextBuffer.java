package mini73;

import java.util.ArrayList;

/**
 * Created by shaun on 29/03/2017.
 */
public class TextBuffer {

    private ArrayList<TextBufferLine> lines = new ArrayList<TextBufferLine>();
    private long decayMillis = 1000;
    public boolean updated = false;

    public void writeLine(String line) {
        lines.add(new TextBufferLine(line));
        updated = true;
        refresh();
    }

    public void refresh() {
        ArrayList<TextBufferLine> toRemove = new ArrayList<TextBufferLine>();
        for (TextBufferLine tbl : lines) {
            if (tbl.hasExpired(decayMillis)) {
                toRemove.add(tbl);
            }
        }

        for (TextBufferLine tbl : toRemove) {
            lines.remove(tbl);
            updated = true;
        }
    }

    @Override
    public String toString() {
        refresh();
        String result = "";
        for (TextBufferLine tbl : lines) {
            result += tbl.toString() + "\n";
        }
        updated = false;
        return result;
    }

    public void setDecayMillis(long decayMillis) {
        this.decayMillis = decayMillis;
    }
}
