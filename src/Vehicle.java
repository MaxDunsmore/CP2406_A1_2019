public class Vehicle {

    String getType() {
        return type;
    }

    private String type;
    private int id;

    int getLength() {
        return length;
    }

    private int length;
    private int location;
    private double roadLocation;
    private char roadDirection;
    private char roadSide;
    private int speed;

    int getSpeed() {
        return speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }



    public Vehicle() {
        type = "Car";
        length = 1;
        location = 0;
        roadLocation = 0;
        roadSide = 'n';
        speed = 0;
    }


    public Vehicle(int length, int location, double roadLocation, char roadSide, int speed, int id, char roadDirection, String type) {
        this.length = length;
        this.location = location;
        this.roadLocation = roadLocation;
        this.roadSide = roadSide;
        this.speed = speed;
        this.id = id;
        this.roadDirection = roadDirection;
        this.type = type;
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
