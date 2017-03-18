package mini73;

/**
 * Created by shaun on 15/12/13.
 */
public class Teleport {

    public int x = 0;
    public int y = 0;
    public Teleport link = null;
    public String name = "Teleport";
    public boolean surface = false;
    public boolean station = false;
    public boolean discovered = false;
    public boolean dock = false;

    public void link(Teleport other) {
        this.link = other;
    }

    public Teleport(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public String getDiscoveredName() {
        String n = "Unknown";
        if (discovered) {
            n = name;
        }
        if (surface) n+= " Surface";
        if (station) n+= " Station";
        if (dock) n+= " Dock";

        return n;
    }
}
