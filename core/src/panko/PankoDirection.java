package panko;

/**
 * Created by shaun on 18/04/2015.
 */
public enum PankoDirection {

    UP, DOWN, LEFT, RIGHT;

    public int dx() {
        if (this.equals(PankoDirection.RIGHT)) return 1;
        if (this.equals(PankoDirection.LEFT)) return -1;
        return 0;
    }

    public int dy() {
        if (this.equals(PankoDirection.UP)) return 1;
        if (this.equals(PankoDirection.DOWN)) return -1;
        return 0;
    }

    public int rotation() {
        if (this.equals(PankoDirection.RIGHT)) return 270;
        if (this.equals(PankoDirection.LEFT)) return 90;
        if (this.equals(PankoDirection.UP)) return 0;
        if (this.equals(PankoDirection.DOWN)) return 180;
        return 0;
    }

    public static PankoDirection random() {
        int choice = Panko.random.nextInt(4);
        if (choice == 0) return PankoDirection.UP;
        if (choice == 1) return PankoDirection.DOWN;
        if (choice == 2) return PankoDirection.LEFT;
        if (choice == 3) return PankoDirection.RIGHT;
        return null;
    }

}
