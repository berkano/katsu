package katsu;

import lombok.Getter;

import java.util.HashMap;

/**
 * Created by shaun on 18/04/2015.
 */
public enum KDirection {

    // dx, dy, rotation
    UP(0,1,0),
    UP_RIGHT(1,1,315),
    RIGHT(1,0,270),
    DOWN_RIGHT(1,-1,225),
    DOWN(0,-1,180),
    DOWN_LEFT(-1,-1,135),
    LEFT(-1,0,90),
    UP_LEFT(-1,1,45);

    static HashMap<Integer, KDirection> fourDirections = new HashMap<Integer, KDirection>();

    static {
        fourDirections.put(0, UP);
        fourDirections.put(1, DOWN);
        fourDirections.put(2, LEFT);
        fourDirections.put(3, RIGHT);
    }

    @Getter private int dx;
    @Getter private int dy;
    @Getter private int rotation;

    KDirection(int dx, int dy, int rotation) {
        this.dx = dx;
        this.dy = dy;
        this.rotation = rotation;
    }

    public static KDirection random() {
        return fourDirections.get(K.random.nextInt(4));
    }

    public static KDirection fromDelta(int req_dx, int req_dy) {

        int dx = 0;
        int dy = 0;

        // Get the sense of the direction
        if (req_dx > 0) dx = 1;
        if (req_dy > 0) dy = 1;
        if (req_dx < 0) dx = -1;
        if (req_dy < 0) dy = -1;

        for (KDirection dir : KDirection.values()) {
            if (dir.getDx() == dx && dir.getDy() == dy) return dir;
        }

        return null;

    }
}
