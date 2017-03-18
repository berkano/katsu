package mini73.entities.base;

import com.badlogic.gdx.Application;
import katsu.KEntity;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 27/04/13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class FixedItem extends Mini73EntityBase {

    @Override
    public void update(Application gc) {
        super.update(gc);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean collide(KEntity other) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
