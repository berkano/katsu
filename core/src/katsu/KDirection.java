package katsu;

/**
 * Created by shaun on 18/04/2015.
 */
public enum KDirection {

    UP, DOWN, LEFT, RIGHT,
    UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;

    public int dx() {
        if (this.equals(KDirection.RIGHT)) return 1;
        if (this.equals(KDirection.LEFT)) return -1;

        if (this.equals(KDirection.DOWN_RIGHT)) return 1;
        if (this.equals(KDirection.UP_RIGHT)) return 1;

        if (this.equals(KDirection.DOWN_LEFT)) return -1;
        if (this.equals(KDirection.UP_LEFT)) return -1;

        return 0;
    }

    public int dy() {
        if (this.equals(KDirection.UP)) return 1;
        if (this.equals(KDirection.UP_LEFT)) return 1;
        if (this.equals(KDirection.UP_RIGHT)) return 1;
        if (this.equals(KDirection.DOWN)) return -1;
        if (this.equals(KDirection.DOWN_LEFT)) return -1;
        if (this.equals(KDirection.DOWN_RIGHT)) return -1;

        return 0;
    }

    public int rotation() {
        if (this.equals(KDirection.RIGHT)) return 270;
        if (this.equals(KDirection.LEFT)) return 90;
        if (this.equals(KDirection.UP)) return 0;
        if (this.equals(KDirection.DOWN)) return 180;
        throw new RuntimeException("couldn't handle direction:" + this);
    }

    public static KDirection random() {
        int choice = K.random.nextInt(4);
        if (choice == 0) return KDirection.UP;
        if (choice == 1) return KDirection.DOWN;
        if (choice == 2) return KDirection.LEFT;
        if (choice == 3) return KDirection.RIGHT;
        throw new RuntimeException("maths stopped working");
    }

    public static KDirection fromDelta(int req_dx, int req_dy) {

        int dx = 0;
        int dy = 0;

        // Get the sense of the direction
        if (req_dx > 0) dx = 1;
        if (req_dy > 0) dy = 1;
        if (req_dx < 0) dx = -1;
        if (req_dy < 0) dy = -1;

        if (dx == 1 && dy == 0) return KDirection.RIGHT;
        if (dx == -1 && dy == 0) return KDirection.LEFT;

        if (dx == 0 && dy == 1) return KDirection.UP;
        if (dx == 0 && dy == -1) return KDirection.DOWN;

        if (dx == 1 && dy == 1) return KDirection.UP_RIGHT;
        if (dx == -1 && dy == 1) return KDirection.UP_LEFT;

        if (dx == 1 && dy == -1) return KDirection.DOWN_RIGHT;
        if (dx == -1 && dy == -1) return KDirection.DOWN_LEFT;

        throw new RuntimeException("couldn't handle delta: " + dx + "," + dy);
    }
}
