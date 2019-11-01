import java.io.Serializable;

class TrafficLight implements Serializable {
    private int location; // location of traffic light on map
    private int roadLocation; // location
    private int colour; // sets the colour of the light - red = 0, green = 1
    private int changedColourTimer;// checks that the light hasn't recently changed colour
    private int trafficLightNumber; // sets what number the traffic light is if it is in a set (intersections)
    private String roadName; // checks what type of the road the traffic light is located on
    private double lightCycle; // checks what part of the light cycle the traffic light is on

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

    void colourTimer() { // rotates through to 50 and when 0 lights can change colours
        if (changedColourTimer >= 0) {
            changedColourTimer += 1;
        }
        if (changedColourTimer == 50) {
            changedColourTimer = 0;
        }
    }

    String getRoadName() {
        return roadName;
    }

    void threeWayCycle() { // rotates the lightCycle and sets the colour for three way road pieces
        switch (roadName) {
            case "threeWayOne":
                if (lightCycle <= 20) {
                    lightCycle++;
                    if (trafficLightNumber == 2) {
                        colour = 1; // green = 1
                    } else if (trafficLightNumber == 3) {
                        colour = 0;
                    } else if (trafficLightNumber == 4) {
                        colour = 0;
                    }
                } else if (lightCycle <= 40) {
                    lightCycle++;
                    if (trafficLightNumber == 2) {
                        colour = 0;
                    } else if (trafficLightNumber == 3) {
                        colour = 1;
                    } else if (trafficLightNumber == 4) {
                        colour = 1;
                    }

                } else if (lightCycle <= 60) {
                    lightCycle++;
                    if (trafficLightNumber == 2) {
                        colour = 1;
                    } else if (trafficLightNumber == 3) {
                        colour = 0;
                    } else if (trafficLightNumber == 4) {
                        colour = 1;
                    }
                } else if (lightCycle <= 80) {
                    lightCycle = 0;
                }
                break;
            case "threeWayTwo":
                if (lightCycle <= 20) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 1; // green = 1
                    } else if (trafficLightNumber == 2) {
                        colour = 0;
                    } else if (trafficLightNumber == 3) {
                        colour = 0;
                    }
                } else if (lightCycle <= 40) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 0;
                    } else if (trafficLightNumber == 2) {
                        colour = 1;
                    } else if (trafficLightNumber == 3) {
                        colour = 1;
                    }

                } else if (lightCycle <= 60) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 1;
                    } else if (trafficLightNumber == 2) {
                        colour = 0;
                    } else if (trafficLightNumber == 3) {
                        colour = 1;
                    }
                } else if (lightCycle <= 80) {
                    lightCycle = 0;
                }
                break;
            case "threeWayThree":
                if (lightCycle <= 20) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 0; // green = 1
                    } else if (trafficLightNumber == 3) {
                        colour = 1;
                    } else if (trafficLightNumber == 4) {
                        colour = 0;
                    }
                } else if (lightCycle <= 40) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 1;
                    } else if (trafficLightNumber == 3) {
                        colour = 0;
                    } else if (trafficLightNumber == 4) {
                        colour = 1;
                    }

                } else if (lightCycle <= 60) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 0;
                    } else if (trafficLightNumber == 3) {
                        colour = 1;
                    } else if (trafficLightNumber == 4) {
                        colour = 1;
                    }
                } else if (lightCycle <= 80) {
                    lightCycle = 0;
                }
                break;
            case "threeWayFour":
                if (lightCycle <= 20) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 0; // green = 1
                    } else if (trafficLightNumber == 2) {
                        colour = 1;
                    } else if (trafficLightNumber == 4) {
                        colour = 0;
                    }
                } else if (lightCycle <= 40) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 1;
                    } else if (trafficLightNumber == 2) {
                        colour = 0;
                    } else if (trafficLightNumber == 4) {
                        colour = 1;
                    }

                } else if (lightCycle <= 60) {
                    lightCycle++;
                    if (trafficLightNumber == 1) {
                        colour = 0;
                    } else if (trafficLightNumber == 2) {
                        colour = 1;
                    } else if (trafficLightNumber == 4) {
                        colour = 1;
                    }
                } else if (lightCycle <= 80) {
                    lightCycle = 0;
                }
                break;
        }
    }

    void fourWayCycle() { // rotates the lightCycle and sets the colour for four way road pieces
        if (lightCycle <= 20) {
            lightCycle = lightCycle + 0.5;
            if (trafficLightNumber == 3) {
                colour = 1; // green = 1
            } else if (trafficLightNumber == 1) {
                colour = 1;
            } else if (trafficLightNumber == 2) {
                colour = 0;
            } else if (trafficLightNumber == 4) {
                colour = 0;
            }
        } else if (lightCycle <= 40) {
            lightCycle++;
            if (trafficLightNumber == 3) {
                colour = 1;
            } else if (trafficLightNumber == 1) {
                colour = 0;
            } else if (trafficLightNumber == 2) {
                colour = 0;
            } else if (trafficLightNumber == 4) {
                colour = 0;
            }

        } else if (lightCycle <= 60) {
            lightCycle = lightCycle + 0.5;
            if (trafficLightNumber == 3) {
                colour = 0;
            } else if (trafficLightNumber == 1) {
                colour = 1;
            } else if (trafficLightNumber == 2) {
                colour = 0;
            } else if (trafficLightNumber == 4) {
                colour = 0;
            }
        } else if (lightCycle <= 80) {
            lightCycle = lightCycle + 0.5;
            if (trafficLightNumber == 3) {
                colour = 0;
            } else if (trafficLightNumber == 1) {
                colour = 0;
            } else if (trafficLightNumber == 2) {
                colour = 1;
            } else if (trafficLightNumber == 4) {
                colour = 1;
            }
        } else if (lightCycle <= 100) {
            lightCycle++;
            if (trafficLightNumber == 3) {
                colour = 0;
            } else if (trafficLightNumber == 1) {
                colour = 0;
            } else if (trafficLightNumber == 2) {
                colour = 0;
            } else if (trafficLightNumber == 4) {
                colour = 1;
            }
        } else if (lightCycle <= 120) {
            if (trafficLightNumber == 3) {
                colour = 0;
            } else if (trafficLightNumber == 1) {
                colour = 0;
            } else if (trafficLightNumber == 2) {
                colour = 1;
            } else if (trafficLightNumber == 4) {
                colour = 0;
            }
            lightCycle = 0;
        }
    }

    TrafficLight(int location, int colour, int roadLocation, int changedColour, int trafficLightNumber, String roadName, int lightCycle) {
        this.location = location;
        this.roadLocation = roadLocation;
        this.colour = colour;
        this.changedColourTimer = changedColour;
        this.trafficLightNumber = trafficLightNumber;
        this.roadName = roadName;
        this.lightCycle = lightCycle;
    }

    void changeColour() { // changes the colour of the traffic light
        if (colour == 0) {
            colour = 1;
        } else if (colour == 1) {
            colour = 0;
        }
    }
}

