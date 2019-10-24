import java.io.Serializable;

class TrafficLight implements Serializable {
    private int location; // location of traffic light on map
    private int roadLocation; // location
    private int colour; // sets the colour of the light - red = 0, green = 1
    private int changedColourTimer;// checks that the light hasn't recently changed colour
    private int trafficLightNumber; // sets what number the traffic light is if it is in a set (intersections)
    private String roadName; // checks what type of the road the traffic light is located on
    private double lightCycle; // checks what part of the light cycle the traffic light is on

    double getLightCycle() {
        return lightCycle;
    }


    int getTrafficLightNumber() {
        return trafficLightNumber;
    }

    int getLocation() {
        return location;
    }

    int getColour() {
        return colour;
    }

    int getRoadLocation() {
        return roadLocation;
    }

    int getChangedColourTimer() {
        return changedColourTimer;
    }

    void colourTimer() { // rotates through to 5 and when 0 lights can change colours
        if (changedColourTimer >= 0){
            changedColourTimer+=1;
        }
        if(changedColourTimer == 5 ){
            changedColourTimer = 0;
        }
    }
    String getRoadName() {
        return roadName;
    }

    void setLightCycle2Way(){ // rotates between the 3 sets of lights for 2-way intersections
        if (lightCycle < 3){
            lightCycle++;
        }
        else if(lightCycle <= 3){
            lightCycle = 0;
        }
    }
    void setLightCycle4Way(){ // rotates between the 6 sets of lights for 4-way intersections, (on for double the time at certain points)
        if (lightCycle >= 1 && lightCycle < 2){
            lightCycle = lightCycle + 0.5;
        }
        else if (lightCycle == 2 || lightCycle == 3){
            lightCycle++;
        }else if (lightCycle >= 4 && lightCycle < 5){
            lightCycle = lightCycle + 0.5;
        }
        else if(lightCycle == 5 ){
            lightCycle++;
        }
        else if (lightCycle == 6) {
            lightCycle = 1;
        }
    }

    TrafficLight(int location, int colour, int roadLocation, int changedColour, int trafficLightNumber, String roadName, int lightCycle){
        this.location = location;
        this.roadLocation = roadLocation;
        this.colour = colour;
        this.changedColourTimer = changedColour;
        this.trafficLightNumber = trafficLightNumber;
        this.roadName = roadName;
        this.lightCycle = lightCycle;
    }
    void printTrafficLight() {
        System.out.println("You added a traffic light to location " + location );
    }
    void changeColour(){ // changes the colour of the traffic light
        if (colour == 0){
            colour = 1;
        }
        else if (colour == 1){
            colour =  0;
        }
    }
    void setGreen(){
        colour =1;
    }
    void setRed(){
        colour =0;
    }
}

