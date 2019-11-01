import java.io.Serializable;

public class Road implements Serializable {
    private String name; // name of the piece of road (e.g. straight, 3/4way - intersections
    private int orientation; // what way the road piece is facing
    private int location; // location of road on map
    private int size; // gets the size of the map to run the simulation - needs to be redesigned

    String getName() {
        return name;
    }

    int getOrientation() {
        return orientation;
    }

    double getLocation() {
        return location;
    }

    public Road(String name, int orientation, int location,int size) {
        this.name = name;
        this.orientation = orientation;
        this.location = location;
        this.size = size;
    }

    int getSize() {
        return size;
    }
}
