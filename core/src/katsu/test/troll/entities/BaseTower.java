package katsu.test.troll.entities;

/**
 * Created by shaun on 23/04/2017.
 */
public class BaseTower extends TrollCastleEntityBase {

    public BaseTower() {
        getAppearance().setSpriteScale(2.0f);
    }

    @Override
    public void onClick() {
        describe("A tower allowing fine views of Trog.");
    }

}
