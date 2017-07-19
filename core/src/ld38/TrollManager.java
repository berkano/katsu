package ld38;

import katsu.KEntity;
import katsu.KInputProcessor;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shaun on 26/04/2017.
 */
public class TrollManager extends KInputProcessor {

    private Troll selectedTroll = null;
    private TrollCastleGame game;
    private TrollMap room;

    private TrollBuilder trollBuilder;
    private TrollAction trollAction;

    Logger logger = LoggerFactory.getLogger(TrollManager.class);
    private boolean hasSpawnedHint = false;


    public TrollManager(TrollCastleGame game, TrollMap room) {
        this.game = game;
        this.room = room;
        trollBuilder = new TrollBuilder(game, this, room);
        trollAction = new TrollAction(game, this, room);
    }

    public void update() {

        // Handle trolls dying
        if (selectedTroll != null) {
            if (selectedTroll.isDestroyed()) {
                selectedTroll = null;
            }
        }

    }

    @Override
    public boolean keyTyped(char character) {

        if (character == 'x') {
            if (selectedTroll != null) {
                selectedTroll.destroy();
            }
        }
        return false;
    }

    private void handleBuildCommand() {
        trollBuilder.build();
    }


    public void handleSpaceCommand() {

        if (selectedTroll == null) {

            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");

        } else {

            List<KEntity> under = room.findEntitiesAtPoint(selectedTroll.getX() + 2, selectedTroll.getY() + 2);
            KEntity highest = null;
            for (KEntity u : under) {
                if (!(u instanceof Troll)) {
                    highest = u;
                }
            }

            if (highest != null) {
                if (highest.getX() != selectedTroll.getX() || highest.getY() != selectedTroll.getY()) {
                }
            }

            trollAction.act(highest);

            if (highest instanceof Sand ||
                    highest instanceof Wall ||
                    highest instanceof BaseTower
                    ) {
                handleBuildCommand();
            }



        }

    }

    public void entityClicked(KEntity clickedEntity) {

        if (clickedEntity instanceof Troll) {

            // After we start selecting stuff, set a reminder about clicking everything else in the map.
            remindAboutClickingMapItems();

            Troll clickedTroll = (Troll) clickedEntity;
            logger.info("Clicked Troll " + clickedTroll.toString());

            if (clickedTroll != selectedTroll) {
                selectNewTroll(clickedTroll);
            } else {
                // it's already selected, so deselect it
                deselectTroll(clickedTroll);
            }

        }

        if (selectedTroll != null) {
            if (clickedEntity != null) {
                if (clickedEntity != selectedTroll) {
                    selectedTroll.setTargetEntity(clickedEntity);
                    if ((clickedEntity instanceof Fish || clickedEntity instanceof Water) && !selectedTroll.getMind().hasHadPsychedelics()) {
                        selectedTroll.say("[RED]Me want fish. Me scared of water. Mushroom stop me being scared.");
                    } else {
                        selectedTroll.say("i go " + clickedEntity.toString());
                    }
                }
            }
        }
    }

    private void deselectTroll(Troll clickedTroll) {
        logger.info("Deselecting Troll " + clickedTroll.toString());
        clickedTroll.getAppearance().setTextureFrom(Troll.class);
        selectedTroll = null;
    }

    private void selectNewTroll(Troll clickedTroll) {

        // deselect existing if we have it
        if (selectedTroll != null) {
            selectedTroll.getAppearance().setTextureFrom(Troll.class);
        }
        logger.info("Selecting Troll " + clickedTroll.toString());
        clickedTroll.onClick();
        selectedTroll = clickedTroll;
        selectedTroll.getAppearance().setTextureFrom(SelectedTroll.class);

    }

    private void remindAboutClickingMapItems() {

        if (!hasSpawnedHint) {
            hasSpawnedHint = true;
            game.task(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    Thread.sleep(30000);
                    game.ui.bottomBar.writeLine("[MAGENTA]Don't forget, you can click things to find out what they are!");
                    game.ui.bottomBar.writeLine("(Deselect any Trolls first by clicking them).");
                    return true;
                }
            });
        }

    }

    public Troll getSelectedTroll() {
        return selectedTroll;
    }
}
