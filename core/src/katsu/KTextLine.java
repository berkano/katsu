package katsu;

public class KTextLine {

    public long added;
    public String text;

    public KTextLine(String s) {
        this.text = s;
        this.added = System.currentTimeMillis();
    }

}
