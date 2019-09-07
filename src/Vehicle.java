public class Vehicle {
    private int length;
    private int location;
    private double roadLocation;
    private char roadDirection;
    private char roadSide;
    private int speed;
    private String type;
    private int id;
    private int chosenDirection;

    String getType() {
        return type;
    }


    int getLength() {
        return length;
    }


    int getSpeed() {
        return speed;
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }


    int getChosenDirection() {
        return chosenDirection;
    }

    void setChosenDirection(int chosenDirection) {
        this.chosenDirection = chosenDirection;
    }

    public Vehicle() {
        type = "Car";
        length = 1;
        location = 0;
        roadLocation = 0;
        roadSide = 'n';
        speed = 0;
        chosenDirection =  0;
    }


    public Vehicle(int length, int location, double roadLocation, char roadSide, int speed, int id, char roadDirection, String type, int chosenDirection) {
        this.length = length;
        this.location = location;
        this.roadLocation = roadLocation;
        this.roadSide = roadSide;
        this.speed = speed;
        this.id = id;
        this.roadDirection = roadDirection;
        this.type = type;
        this.chosenDirection = chosenDirection;
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
    void accelerateVehicle(){
        if (speed < 5) {
            speed++;
        }
    }
    void decelerateVehicle(){
        if (speed >= 2) {
           speed--;
        }
        else {
            speed =1;
        }
    }
    void stopVehicle(){
        speed = 0;
    }
    void moveVehicleRoadLocation(){
        roadLocation = roadLocation + speed;
    }
}
