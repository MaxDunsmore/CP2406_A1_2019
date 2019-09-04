class TrafficLight {
    private int location;
    private int colour;
    private char roadLocation;
    private int changedColourTimer;

    int getLocation() {
        return location;
    }

    int getColour() {
        return colour;
    }

    char getRoadLocation() {
        return roadLocation;
    }

    int getChangedColourTimer() {
        return changedColourTimer;
    }

    void colourTimer() {
        if (changedColourTimer >= 0){
            changedColourTimer+=1;
        }
        if(changedColourTimer == 5 ){
            changedColourTimer = 0;
        }
    }

    TrafficLight(int location, int colour, char roadLocation, int changedColour){
        this.location = location;
        this.roadLocation = roadLocation;
        this.colour = colour;
        this.changedColourTimer = changedColour;
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

