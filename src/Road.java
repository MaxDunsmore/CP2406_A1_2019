import java.io.Serializable;

public class Road implements Serializable {
    private String name;
    private int orientation;
    private int location;

    String getName() {
        return name;
    }

    int getOrientation() {
        return orientation;
    }

    double getLocation() {
        return location;
    }

    public Road() {
        name = "";
        orientation = 0;
        location = 0;
    }

    public Road(String name, int orientation, int location) {
        this.name = name;
        this.orientation = orientation;
        this.location = location;
    }

    void printRoad() {
        System.out.println("You added a piece of road to your map");
        System.out.println("Name: " + name + "\nOrientation: " + orientation + "\nLocation: " + location);
    }
}
