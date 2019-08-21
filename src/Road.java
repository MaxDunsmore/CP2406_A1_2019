public class Road {
    private String name;
    private int orientation;
    private double location;

    String getName() {
        return name;
    }

    int getOrientation() {
        return orientation;
    }

    double getLocation() {
        return location;
    }

    public Road(String name, int orientation, double location) {
        this.name = name;
        this.orientation = orientation;
        this.location = location;
    }
    void printRoad(){
        System.out.println("You added a piece of road to your map");
        System.out.println("Name: " + name + "\nOrientation: " + orientation + "\nLocation: " + location);

    }
}
