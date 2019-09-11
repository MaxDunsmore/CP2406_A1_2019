class TrafficLight {
    private int location;
    private char roadLocation;
    private int colour;
    private int changedColourTimer;
    private int trafficLightNumber;
    private String roadName;
    private double lightCycle;

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

    String getRoadName() {
        return roadName;
    }

    void setLightCycle2Way(){
        if (lightCycle < 3){
            lightCycle++;
        }
        else if(lightCycle <= 3){
            lightCycle = 0;
        }
    }
    void setLightCycle4Way(){
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

    TrafficLight(int location, int colour, char roadLocation, int changedColour, int trafficLightNumber, String roadName, int lightCycle){
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

