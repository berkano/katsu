package katsu;

import java.util.ArrayList;

/**
 * Created by shaun on 29/03/2017.
 */
public class KTextBuffer {

    private ArrayList<KTextBufferLine> lines = new ArrayList<KTextBufferLine>();
    private long decayMillis = 1000;
    public boolean updated = false;
    private int lineLimit = -1;

    public void writeLine(String line, long decayMillis) {
        lines.add(new KTextBufferLine(line, decayMillis));
        updated = true;
        refresh();
    }

    public void refresh() {

        ArrayList<KTextBufferLine> toRemove = new ArrayList<KTextBufferLine>();

        // Remove anything that has expired
        for (KTextBufferLine tbl : lines) {
            if (tbl.hasExpired()) {
                toRemove.add(tbl);
            }
        }

        for (KTextBufferLine tbl : toRemove) {
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
        for (KTextBufferLine tbl : lines) {
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
