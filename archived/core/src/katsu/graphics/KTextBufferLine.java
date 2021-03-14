package katsu.graphics;

/**
 * Created by shaun on 29/03/2017.
 */
public class KTextBufferLine {

    private final long decayMillis;
    private String string;
    private long created = System.currentTimeMillis();

    public KTextBufferLine(String string, long decayMillis) {
        this.string = string;
        this.decayMillis = decayMillis;
    }

    @Override
    public String toString() {
        return string;
    }

    public boolean hasExpired() {
        return this.created < System.currentTimeMillis() - this.decayMillis;
    }
}
