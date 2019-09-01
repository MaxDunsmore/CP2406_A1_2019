public class Vehicle {


    private int id;
    private int length;
    private int location;
    private double roadLocation;
    private char roadDirection;
    private char roadSide;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private int speed;

    public Vehicle() {
        length = 1;
        location = 0;
        roadLocation = 0;
        roadSide = 'n';
        speed = 0;
    }


    public Vehicle(int length, int location, double roadLocation, char roadSide, int speed, int id, char roadDirection) {
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

    double getRoadLocation() {
        return roadLocation;
    }

    void setRoadLocation(double roadLocation) {
        this.roadLocation = roadLocation;
    }

    char getRoadSide() {
        return roadSide;
    }

    void setRoadSide(char roadSide) {
        this.roadSide = roadSide;
    }

    char getRoadDirection() {
        return roadDirection;
    }

    void setRoadDirection(char roadDirection) {
        this.roadDirection = roadDirection;
    }
}
