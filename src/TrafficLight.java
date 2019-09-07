class TrafficLight {
    private int location;
    private int colour;
    private char roadLocation;
    private int changedColourTimer;
    private int trafficLightNumber;
    private String roadType;
    private int lightCycle;

    public int getLightCycle() {
        return lightCycle;
    }


    public int getTrafficLightNumber() {
        return trafficLightNumber;
    }

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

    public String getRoadType() {
        return roadType;
    }

    public void setLightCycle2Way(){
        if (lightCycle < 3){
            lightCycle++;
        }
        else if(lightCycle <= 3){
            lightCycle = 0;
        }
    }

    TrafficLight(int location, int colour, char roadLocation, int changedColour, int trafficLightNumber, String roadType, int lightCycle){
        this.location = location;
        this.roadLocation = roadLocation;
        this.colour = colour;
        this.changedColourTimer = changedColour;
        this.trafficLightNumber = trafficLightNumber;
        this.roadType = roadType;
        this.lightCycle = lightCycle;
    }
    void printTrafficLight() {
        System.out.println("You added a traffic light to location " + location );
    }
    void changeColour(){
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

