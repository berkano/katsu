package mini73;

/**
 * Created by shaun on 29/03/2017.
 */
public class TextBufferLine {

    private String string;
    private long created = System.currentTimeMillis();

    public TextBufferLine(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public boolean hasExpired(long decayMillis) {
        return this.created < System.currentTimeMillis() - decayMillis;
    }
}
