package katsu;

/**
 * Created by shaun on 18/04/2015.
 */
public enum KDirection {

    UP, DOWN, LEFT, RIGHT;

    public int dx() {
        if (this.equals(KDirection.RIGHT)) return 1;
        if (this.equals(KDirection.LEFT)) return -1;
        return 0;
    }

    public int dy() {
        if (this.equals(KDirection.UP)) return 1;
        if (this.equals(KDirection.DOWN)) return -1;
        return 0;
    }

    public int rotation() {
        if (this.equals(KDirection.RIGHT)) return 270;
        if (this.equals(KDirection.LEFT)) return 90;
        if (this.equals(KDirection.UP)) return 0;
        if (this.equals(KDirection.DOWN)) return 180;
        return 0;
    }

    public static KDirection random() {
        int choice = K.random.nextInt(4);
        if (choice == 0) return KDirection.UP;
        if (choice == 1) return KDirection.DOWN;
        if (choice == 2) return KDirection.LEFT;
        if (choice == 3) return KDirection.RIGHT;
        // blah
        return KDirection.UP;
    }

}
