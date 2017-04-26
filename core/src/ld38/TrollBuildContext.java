package ld38;

import ld38.entities.Troll;

/**
 * Created by shaun on 26/04/2017.
 */
public class TrollBuildContext {

    private TrollCastleGame game;
    private Map room;
    private Troll selectedTroll;

    public TrollCastleGame getGame() {
        return game;
    }

    public void setGame(TrollCastleGame game) {
        this.game = game;
    }

    public Map getRoom() {
        return room;
    }

    public void setRoom(Map room) {
        this.room = room;
    }

    public void setSelectedTroll(Troll selectedTroll) {
        this.selectedTroll = selectedTroll;
    }

    public Troll getSelectedTroll() {
        return selectedTroll;
    }
}
