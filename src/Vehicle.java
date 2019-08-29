public class Vehicle {


    private int id;
    private int length;
    private int location;
    private int roadLocation;
    private char roadDirection;
    private char roadSide;
    private int speed;

    public Vehicle() {
        length = 1;
        location = 0;
        roadLocation = 0;
        roadSide = 'n';
        speed = 0;
    }


    public Vehicle(int length, int location, int roadLocation, char roadSide, int speed, int id, char roadDirection) {
        this.length = length;
        this.location = location;
        this.roadLocation = roadLocation;
        this.roadSide = roadSide;
        this.speed = speed;
        this.id = id;
        this.roadDirection = roadDirection;
    }

    int getId() {
        return id;
    }

    int getLocation() {
        return location;
    }

    void setLocation(int location) {
        this.location = location;
    }

    int getRoadLocation() {
        return roadLocation;
    }

    public void setRoadLocation(int roadLocation) {
        this.roadLocation = roadLocation;
    }

    public char getRoadSide() {
        return roadSide;
    }

    void setRoadSide(char roadSide) {
        this.roadSide = roadSide;
    }
}
