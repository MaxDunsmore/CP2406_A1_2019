import java.io.Serializable;

public class Road implements Serializable {
    private String name; // name of the piece of road (e.g. straight, 3/4way - intersections
    private int orientation; // what way the road piece is facing
    private int location; // location of road on map
    private int size;

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

    public Road(String name, int orientation, int location,int size) {
        this.name = name;
        this.orientation = orientation;
        this.location = location;
        this.size = size;
    }

    void printRoad() { // displays road once piece is added to map (roadList)
        System.out.println("You added a piece of road to your map");
        System.out.println("Name: " + name + "\nOrientation: " + orientation + "\nLocation: " + location);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
