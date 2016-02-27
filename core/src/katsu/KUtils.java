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


}
