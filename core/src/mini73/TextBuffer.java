package mini73;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shaun on 29/03/2017.
 */
public class TextBuffer {

    private ArrayList<TextBufferLine> lines = new ArrayList<TextBufferLine>();
    private long decayMillis = 1000;
    public boolean updated = false;
    private int lineLimit = -1;

    public void writeLine(String line, long decayMillis) {
        lines.add(new TextBufferLine(line, decayMillis));
        updated = true;
        refresh();
    }

    public void refresh() {

        ArrayList<TextBufferLine> toRemove = new ArrayList<TextBufferLine>();

        // Remove anything that has expired
        for (TextBufferLine tbl : lines) {
            if (tbl.hasExpired()) {
                toRemove.add(tbl);
            }
        }

        for (TextBufferLine tbl : toRemove) {
            lines.remove(tbl);
            updated = true;
        }

        // Enforce line limit
        if (lineLimit >= 0) {
            int nRemove = lines.size() - lineLimit;
            if (nRemove > 0) {
                for (int i = 0; i < nRemove; i++) {
                    lines.remove(0);
                    updated = true;
                }
            }
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

    public void clear() {
        lines = new ArrayList<>();
        updated = true;
    }

    public int getSize() {
        return lines.size();
    }

    public void setLineLimit(int lineLimit) {
        this.lineLimit = lineLimit;
    }
}
