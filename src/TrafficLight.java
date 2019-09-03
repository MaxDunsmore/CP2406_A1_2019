class TrafficLight {
    private int location;
    private int colour;
    private char roadLocation;

    int getLocation() {
        return location;
    }

    int getColour() {
        return colour;
    }

    void setColour(int colour) {
        this.colour = colour;
    }

    char getRoadLocation() {
        return roadLocation;
    }

    TrafficLight(int location, int colour, char roadLocation){
        this.location = location;
        this.roadLocation = roadLocation;
        this.colour = colour;
    }
    void printTrafficLight() {
        System.out.println("You added a traffic light to location " + location );
    }
    void changeColour(){
        if (colour < 1){
            colour = 1;
        }
        else if (colour == 1){
            colour =  0;
        }
    }
}

