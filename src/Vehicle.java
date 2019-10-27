public class Vehicle {
    private int length;// length of the vehicle
    private int location;// location of the vehicle on the map
    private double roadLocation;
    private int carX;
    private int carY;
    private char roadDirection;// used to control which way a vehicle is moving and what side of the road it is on
    private int speed; // current speed of the vehicle
    private String name; // name of the vehicle (e.g. car, bus, bike)
    private int id; // use to select singular vehicles for adding them to the map
    private int chosenDirection; // used when at a intersection to decide what way the vehicle is going


    int getCarX() {
        return carX;
    }

    void setCarX(int carX) {
        this.carX = carX;
    }

    int getCarY() {
        return carY;
    }

    void setCarY(int carY) {
        this.carY = carY;
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


    public Vehicle(int length, int location,double roadLocation, int carX, int carY,int speed, int id, char roadDirection, String name, int chosenDirection) {
        this.length = length;
        this.location = location;
        this.roadLocation = roadLocation;
        this.carX = carX;
        this.carY = carY;
        this.speed = speed;
        this.id = id;
        this.roadDirection = roadDirection;
        this.name = name;
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

    char getRoadDirection() {
        return roadDirection;
    }

    void setRoadDirection(char roadDirection) {
        this.roadDirection = roadDirection;
    }
    void accelerateVehicle(){ // increases vehicle up to 5 (max speed limit for city)
        if (speed < 4) {
            speed++;
        }
    }
    void decelerateVehicle(){ // decreases vehicle speed down to 1
        if (speed == 4){
            speed-= 1;
        }
        else if (speed >= 2) {
           speed--;
        }
        else {
            speed =2;
        }
    }
    void stopVehicle(){ // sets vehicle speed to 0 (stops movement)
        speed = 0;
    }
    void moveVehicleRoadLocation(){ // sets the vehicles next location based of speed
        roadLocation += speed;
    }
}
