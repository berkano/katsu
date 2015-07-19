package katsu;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 13/04/13
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
public class KTextLine {

    public long added;
    public String text;

    public KTextLine(String s) {
        this.text = s;
        this.added = System.currentTimeMillis();
    }

}
