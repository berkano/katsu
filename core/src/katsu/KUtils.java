package katsu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 26/02/2016.
 */
public class KUtils {

    public long currentTime() {
        return System.currentTimeMillis();
    }

    public List<Class> buildClassList(Class... classes ) {
        ArrayList<Class> classList = new ArrayList<Class>();
        for (Class clazz : classes) {
            classList.add(clazz);
        }
        return classList;

    }

    public String wrap(String in, int len) {
        in = in.trim();
        if (in.length() < len) return in;
        if (in.substring(0, len).contains("\n"))
            return in.substring(0, in.indexOf("\n")).trim() + "\n\n" + wrap(in.substring(in.indexOf("\n") + 1), len);
        int place = Math.max(Math.max(in.lastIndexOf(" ", len), in.lastIndexOf("\t", len)), in.lastIndexOf("-", len));
        return in.substring(0, place).trim() + "\n" + wrap(in.substring(place), len);
    }


}
