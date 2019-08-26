public class Vehicle {
    private int length;
    private int location;
    private int roadLocation;
    private int roadSide;

    public Vehicle() {
        length = 1;
        location = 0;
        roadLocation = 0;
        roadSide = 0;
    }

    public Vehicle(int length, int location, int roadSize, int roadSide) {
        this.length = length;
        this.location = location;
        this.roadLocation = roadSize;
        this.roadSide = roadSide;
    }
}
