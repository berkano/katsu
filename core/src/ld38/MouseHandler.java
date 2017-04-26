package ld38;

import katsu.KInputProcessor;

/**
 * Created by shaun on 26/04/2017.
 */
public class MouseHandler extends KInputProcessor {

    private int lastDragX = 0;
    private int lastDragY = 0;
    private boolean hasDragged = false;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        hasDragged = false;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
    }


}
