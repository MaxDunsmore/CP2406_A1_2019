import java.util.*;

public class Simulation extends TimerTask {

    private final int vehicles;
    private final int map;
    private final ArrayList<Road> roadArrayList;
    private final ArrayList<Vehicle> vehiclesArrayList;
    private final ArrayList<TrafficLight> trafficLightArrayList;
    private boolean checkMapLocation = true;
    //private int carsOnMap = 0; // use later when displaying cars each rotation
    private int[] topMap;
    private int[] bottomMap;
    private int[] leftMap;
    private int[] rightMap;
    private boolean accelerateVehicle = true;
    private Timer timer;

    Simulation(int vehicles, ArrayList<Road> roadArrayList, ArrayList<TrafficLight> trafficLightArrayList, int[] topMap, int[] bottomMap, int[] leftMap, int[] rightMap, ArrayList<Vehicle> vehiclesArrayList, int map, Timer timer) {
        this.vehicles = vehicles;
        this.roadArrayList = roadArrayList;
        this.trafficLightArrayList = trafficLightArrayList;
        this.topMap = topMap;
        this.bottomMap = bottomMap;
        this.leftMap = leftMap;
        this.rightMap = rightMap;
        this.vehiclesArrayList = vehiclesArrayList;
        this.map = map;
        this.timer = timer;
    }

    @Override
    public void run() {
        char roadSideR = 'r';
        char roadSideL = 'l';
        int roadLocation = 1;
        int roadLength = 20;
        int mapSize = map * map;
        int min = 1;
        int max = 2;
        int max4Way = 3;
        char down = 'd';
        char up = 'u';
        char left = 'l';
        char right = 'r';
        boolean moveVehicle = true;
        Random rand = new Random();

        changeTrafficLightColour(rand);
        if (vehicles > 0) { // check if cars can enter each side of the map
            enterTopMap(roadSideR, roadLocation);
            enterBottomMap(roadSideL, roadLocation);
            enterLeftMap(roadSideL, roadLocation);
            enterRightMap(roadSideR, roadLocation);
        }
        // used for testing traffic lights
/*        if (count >= 12) {
            for (TrafficLight trafficLight1 : trafficLightArrayList) {
                trafficLight1.setGreen();
            }
        } else {
            for (TrafficLight trafficLight1 : trafficLightArrayList) {
                trafficLight1.setRed();
            }
        }
        count++;
*/
        for (Vehicle v : vehiclesArrayList) {
            if (v.getLocation() > 0 && v.getLocation() <= mapSize) {
                double nextRoadLocation = v.getRoadLocation() + v.getSpeed();
                if (v.getRoadDirection() == down) { // once down is complete, add code for vehicles moving different directions
                    for (Road r : roadArrayList) {
                        if (r.getLocation() == v.getLocation()) {
                            switch (r.getName()) {
                                case "Straight":
                                    if (r.getOrientation() == 1) {
                                        // check if car is in front
                                        for (Vehicle v2 : vehiclesArrayList) {
                                            if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation()) {
                                                checkVehicleInFront(v, nextRoadLocation, r, v2);
                                            } else {
                                                // else no cars in front, check for traffic lights
                                                for (TrafficLight t : trafficLightArrayList) {
                                                    if (t.getLocation() == v.getLocation()) {
                                                        if (t.getRoadLocation() == 'b' && t.getColour() > 1) {
                                                            decelerateTrafficLight(v, nextRoadLocation, 15, 20, 19);

                                                        } else if (t.getRoadLocation() == 't' && t.getColour() > 1) {
                                                            stopVehicleStraightTrafficLight(roadLength, v, t);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case "2-Way intersection":
                                    checkForDeceleration(v, nextRoadLocation, r);
                                    if (accelerateVehicle) {
                                        if (r.getOrientation() == 2) {
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(min, max, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() != down) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(left);
                                                                v.setRoadDirection(left);
                                                            }
                                                        }
                                                    }
                                                } else if (v.getChosenDirection() == 2) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() == right) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(right);
                                                                v.setRoadDirection(right);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                // slow down vehicle
                                                v.decelerateVehicle();
                                                v.moveVehicleRoadLocation();
                                                accelerateVehicle = false;
                                            }
                                        } else if (r.getOrientation() == 3) {
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(1, 2, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() == up) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(left);
                                                                v.setRoadDirection(left);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                // slow down vehicle
                                                v.decelerateVehicle();
                                                v.moveVehicleRoadLocation();
                                                accelerateVehicle = false;
                                            }
                                        } else if (r.getOrientation() == 4) {
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(1, 2, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    v.setRoadLocation(1);
                                                    v.setRoadSide('l');
                                                    v.setRoadDirection('r');
                                                } else if (v.getChosenDirection() == 2) {
                                                    break;
                                                }
                                            } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                v.decelerateVehicle();
                                                v.moveVehicleRoadLocation();
                                                System.out.println("Speed: " + v.getSpeed());
                                                accelerateVehicle = false;
                                            }
                                        } else if (r.getOrientation() == 1) {
                                            if (v.getSpeed() + v.getRoadLocation() >= 9) {
                                                setRoadLocation(10, v, map);
                                                v.moveVehicleRoadLocation();
                                                accelerateVehicle = true;
                                            }
                                        }
                                    }
                                    break;
                                case "4-way intersection":
                                    for (Vehicle v2 : vehiclesArrayList) {
                                        if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation() && v.getId() != v2.getId()) {
                                            checkVehicleInFront(v, nextRoadLocation, r, v2);
                                        } else {
                                            for (TrafficLight t : trafficLightArrayList) {
                                                // add code for 4 way intersection traffic lights
                                                if (t.getLocation() == v.getLocation()) {
                                                    if (t.getColour() == 0) { // 0 is red
                                                        if (t.getTrafficLightNumber() == 1 || t.getTrafficLightNumber() == 2 || t.getTrafficLightNumber() == 3 || t.getTrafficLightNumber() == 4) {
                                                            if (v.getRoadLocation() >= 5 && v.getRoadLocation() <= 10) {
                                                                decelerateTrafficLight(v, nextRoadLocation, 5, 10, 9);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (accelerateVehicle) {
                                        // 4 way intersection movement code
                                        // if(nextRoadLocation > 5 && nextRoadLocation < 10){slow down vehicle}
                                        if (nextRoadLocation > 5 && nextRoadLocation < 9) {
                                            v.decelerateVehicle();
                                            v.moveVehicleRoadLocation();
                                            accelerateVehicle = false;
                                        } else if (nextRoadLocation >= 9 && nextRoadLocation <= 11)// add code above to slow down vehicle - think it is skipping it ( goes from 6 to 11) - make this code else if
                                        {
                                            getDirection(min, max4Way, rand, v);
                                            v.setRoadLocation(10);
                                            if (nextRoadLocation <= 10 && v.getSpeed() != 0) {
                                                v.setRoadLocation(10);
                                                v.setSpeed(0);
                                                accelerateVehicle = false;
                                                moveVehicle = false;
                                            } else if (v.getRoadLocation() >= 10) {
                                                if (v.getChosenDirection() == 1) {
                                                    for (Vehicle v3 : vehiclesArrayList) {
                                                        if (v3.getRoadLocation() == 10 || v3.getRoadLocation() == 11 && v.getRoadDirection() == right) {
                                                            if (v3.getRoadDirection() == right) {
                                                                v.stopVehicle();
                                                                accelerateVehicle = false;
                                                                moveVehicle = false;
                                                            }
                                                        }
                                                    }
                                                    if (moveVehicle) {
                                                        v.setRoadLocation(11);
                                                        v.setRoadDirection(right);
                                                        v.setRoadSide(left);
                                                    }

                                                } else if (v.getChosenDirection() == 2) {
                                                    for (Vehicle v3 : vehiclesArrayList) {
                                                        if (v3.getRoadLocation() == 10 || v3.getRoadLocation() == 11) {
                                                            if (v3.getRoadDirection() == right || v3.getRoadDirection() == left) {
                                                                v.stopVehicle();
                                                                accelerateVehicle = false;
                                                                moveVehicle = false;
                                                            }

                                                        }
                                                    }
                                                    if (moveVehicle) {
                                                        v.setRoadLocation(11);
                                                        v.setRoadDirection(down);
                                                        v.setRoadSide(right);
                                                    }
                                                } else if (v.getChosenDirection() == 3) {
                                                    for (Vehicle v3 : vehiclesArrayList) {
                                                        if (v3.getRoadLocation() == 10 || v3.getRoadLocation() == 11) {
                                                            if (v3.getRoadDirection() == right || v3.getRoadDirection() == left || v3.getRoadDirection() == up) {
                                                                v.stopVehicle();
                                                                accelerateVehicle = false;
                                                                moveVehicle = false;
                                                            }
                                                        }
                                                    }
                                                    if (moveVehicle) {
                                                        v.setRoadLocation(11);
                                                        v.setRoadDirection(right);
                                                        v.setRoadSide(left);
                                                    }
                                                }
                                                accelerateVehicle = false;
                                            }


                                        }

                                    }
                            }
                        }
                    }
                } else if ((v.getRoadDirection() == left)) {
                    for (Road r : roadArrayList) {
                        if (r.getLocation() == v.getLocation()) {
                            switch (r.getName()) {
                                case "Straight":
                                    if (r.getOrientation() == 2) {
                                        // check if car is in front
                                        for (Vehicle v2 : vehiclesArrayList) {
                                            if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation()) {
                                                checkVehicleInFront(v, nextRoadLocation, r, v2);
                                            } else {
                                                // else no cars in front, check for traffic lights
                                                for (TrafficLight t : trafficLightArrayList) {
                                                    if (t.getLocation() == v.getLocation()) {
                                                        if (t.getRoadLocation() == 'b' && t.getColour() > 1) {
                                                            decelerateTrafficLight(v, nextRoadLocation, 15, 20, 19);

                                                        } else if (t.getRoadLocation() == 't' && t.getColour() > 1) {
                                                            stopVehicleStraightTrafficLight(roadLength, v, t);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                case "2-Way intersection":
                                    checkForDeceleration(v, nextRoadLocation, r);
                                    // ADD 2 WAY CODE HERE |͟
                                    if (accelerateVehicle) {
                                        if (r.getOrientation() == 2) {
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(min, max, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() != down) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(left);
                                                                v.setRoadDirection(left);
                                                            }
                                                        }
                                                    }
                                                } else if (v.getChosenDirection() == 2) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() == right) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(left);
                                                                v.setRoadDirection(up);
                                                            }
                                                        }
                                                    }
                                                } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                    // slow down vehicle
                                                    v.decelerateVehicle();
                                                    v.moveVehicleRoadLocation();
                                                    accelerateVehicle = false;
                                                }
                                            }
                                        } else if (r.getOrientation() == 4) { // |~
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(min, max, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() == down) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(left);
                                                                v.setRoadDirection(left);
                                                            }
                                                        }
                                                    }
                                                } else if (v.getChosenDirection() == 2) {
                                                    for (Vehicle v2 : vehiclesArrayList) {
                                                        if (v2.getLocation() == v.getLocation()) {
                                                            if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() == down || v2.getRoadDirection() == up) {
                                                                accelerateVehicle = false;
                                                                v.stopVehicle();
                                                            } else {
                                                                v.setRoadLocation(11);
                                                                v.setRoadSide(right);
                                                                v.setRoadDirection(right);
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                // slow down vehicle
                                                v.decelerateVehicle();
                                                v.moveVehicleRoadLocation();
                                                accelerateVehicle = false;
                                            }
                                        } else if (r.getOrientation() == 1) { // T
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(min, max, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    v.setRoadLocation(11);
                                                    v.setRoadDirection(left);
                                                } else if (v.getChosenDirection() == 2) {
                                                    v.setRoadLocation(11);
                                                }
                                            } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                // slow down vehicle
                                                v.decelerateVehicle();
                                                v.moveVehicleRoadLocation();
                                                accelerateVehicle = false;
                                            }
                                        } else if (r.getOrientation() == 3) { // -|
                                            if (nextRoadLocation >= 9 && nextRoadLocation <= 10) {
                                                getDirection(min, max, rand, v);
                                                if (v.getChosenDirection() == 1) {
                                                    v.setRoadLocation(11);
                                                    v.setRoadDirection(left);
                                                } else if (v.getChosenDirection() == 2) {
                                                    v.setRoadLocation(11);
                                                }
                                            } else if (nextRoadLocation >= 5 && nextRoadLocation < 9) {
                                                // slow down vehicle
                                                v.decelerateVehicle();
                                                v.moveVehicleRoadLocation();
                                                accelerateVehicle = false;
                                            }
                                        }
                                    }
                                case "4-Way intersection":
                                    for (Vehicle v2 : vehiclesArrayList) {
                                        if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation() && v.getId() != v2.getId()) {
                                            checkVehicleInFront(v, nextRoadLocation, r, v2);
                                        } else {
                                            for (TrafficLight t : trafficLightArrayList) {
                                                // add code for 4 way intersection traffic lights
                                                if (t.getLocation() == v.getLocation()) {
                                                    if (t.getColour() == 0) { // 0 is red
                                                        if (t.getTrafficLightNumber() == 1 || t.getTrafficLightNumber() == 2 || t.getTrafficLightNumber() == 3 || t.getTrafficLightNumber() == 4) {
                                                            if (v.getRoadLocation() >= 5 && v.getRoadLocation() <= 10) {
                                                                decelerateTrafficLight(v, nextRoadLocation, 5, 10, 9);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (accelerateVehicle) {
                                        // 4 way intersection movement code
                                        if (nextRoadLocation > 9 && nextRoadLocation < 11) {
                                            getDirection(min, max4Way, rand, v);
                                            v.setRoadLocation(10);
                                            if (nextRoadLocation == 10 && v.getSpeed() != 0) {
                                                v.setRoadLocation(10);
                                                v.setSpeed(0);
                                                accelerateVehicle = false;
                                                moveVehicle = false;
                                            } else if (v.getRoadLocation() >= 10) {
                                                if (v.getChosenDirection() == 1) {
                                                    for (Vehicle v3 : vehiclesArrayList) {
                                                        if (v3.getRoadLocation() == 10 || v3.getRoadLocation() == 11) {
                                                            if (v3.getRoadDirection() == right || v3.getRoadDirection() == down) {
                                                                v.stopVehicle();
                                                                accelerateVehicle = false;
                                                                moveVehicle = false;
                                                            }
                                                        }
                                                    }
                                                    if (moveVehicle) {
                                                        v.setRoadLocation(11);
                                                        v.setRoadDirection(right);
                                                        v.setRoadSide(left);
                                                    }

                                                } else if (v.getChosenDirection() == 2) {
                                                    for (Vehicle v3 : vehiclesArrayList) {
                                                        if (v3.getRoadLocation() == 10 || v3.getRoadLocation() == 11) {
                                                            if (v3.getRoadDirection() == right || v3.getRoadDirection() == down) {
                                                                v.stopVehicle();
                                                                accelerateVehicle = false;
                                                                moveVehicle = false;
                                                            }

                                                        }
                                                    }
                                                    if (moveVehicle) {
                                                        v.setRoadLocation(11);
                                                        v.setRoadDirection(down);
                                                    }
                                                } else if (v.getChosenDirection() == 3) {
                                                    for (Vehicle v3 : vehiclesArrayList) {
                                                        if (v3.getRoadLocation() == 10 || v3.getRoadLocation() == 11) {
                                                            if (v3.getRoadDirection() == right || v3.getRoadDirection() == down) {
                                                                v.stopVehicle();
                                                                accelerateVehicle = false;
                                                                moveVehicle = false;
                                                            }
                                                        }
                                                    }
                                                    if (moveVehicle) {
                                                        v.setRoadLocation(11);
                                                        v.setRoadDirection(left);
                                                    }
                                                }
                                                accelerateVehicle = false;
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
            if (accelerateVehicle) { // runs if nothing is causing the vehicle to decelerate
                if (v.getLocation() <= mapSize && v.getRoadLocation() > 0) {
                    if (v.getSpeed() < 5) {
                        v.setSpeed(v.getSpeed() + 1);
                    }
                    if (v.getRoadDirection() == down) {
                        setRoadLocation(roadLength, v, 4);
                    } else if (v.getRoadDirection() == left) {
                        setRoadLocation(roadLength, v, -1);
                    } else if (v.getRoadDirection() == right) {
                        setRoadLocation(roadLength, v, +1);
                    } else if (v.getRoadDirection() == up) {
                        setRoadLocation(roadLength, v, -4);
                    }
                }
            }
            if (v.getLocation() <= mapSize && v.getRoadLocation() > 0) {
                System.out.println(v.getName() + " " + v.getId() + " Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed() * 10 + ". Road side: " + v.getRoadSide() + ". Direction: " + v.getRoadDirection());
            }
            accelerateVehicle = true;
        }
        boolean endSimulation = true;
        for (Vehicle v : vehiclesArrayList) {
            if (v.getRoadLocation() <= mapSize) {
                endSimulation = false;
                break;
            }
        }
        if (endSimulation) {
            System.out.println("No cars left on map");
            timer.cancel();
            timer.purge();
        }
        System.out.println("__________________________________________________________");
    }

    private void checkForDeceleration(Vehicle v, double nextRoadLocation, Road r) {
        for (Vehicle v2 : vehiclesArrayList) {
            if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation() && v.getId() != v2.getId()) {
                checkVehicleInFront(v, nextRoadLocation, r, v2);
            } else {
                for (TrafficLight t : trafficLightArrayList) {
                    if (t.getLocation() == v.getLocation()) {
                        if (t.getColour() == 0) { // 0 is red
                            if (t.getTrafficLightNumber() == 1 || t.getTrafficLightNumber() == 2 || t.getTrafficLightNumber() == 3) {
                                if (v.getLocation() == t.getLocation()) {
                                    decelerateTrafficLight(v, nextRoadLocation, 5, 10, 9);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void changeTrafficLightColour(Random rand) {
        String trafficLightColour = "red";
        int trafficLight;
        int trafficLight2Way = rand.nextInt(((100 - 1) + 1)) + 1;
        int trafficLight4Way = rand.nextInt(((100 - 1) + 1)) + 1;


        for (TrafficLight t : trafficLightArrayList) {
            trafficLight = rand.nextInt(((100 - 1) + 1)) + 1;
            // if Straight run below code
            switch (t.getRoadName()) {
                case "Straight":
                    if (t.getChangedColourTimer() == 0) {
                        if (trafficLight > 50) {
                            t.changeColour();
                            if (t.getColour() == 1) {
                                trafficLightColour = "green";
                            } else if (t.getColour() == 0) {
                                trafficLightColour = "red";
                            }
                            System.out.println("Traffic light at " + t.getLocation() + " turned " + trafficLightColour);
                            t.colourTimer();
                        }
                    } else {
                        t.colourTimer();
                    }
                    break;
                case "2-Way intersection":
                    if (t.getChangedColourTimer() == 0) {
                        if (trafficLight2Way > 50) {
                            t.setLightCycle2Way();
                            if (t.getLightCycle() == 1) {
                                if (t.getTrafficLightNumber() == 1) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 3) {
                                    t.setRed();
                                }
                            } else if (t.getLightCycle() == 2) {
                                if (t.getTrafficLightNumber() == 1) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 3) {
                                    t.setRed();
                                }
                            } else if (t.getLightCycle() == 3) {
                                // code for changing 3 to 1 (0) light cycle
                                if (t.getTrafficLightNumber() == 1) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 3) {
                                    t.setGreen();
                                }
                            }


                            if (t.getColour() == 1) {
                                trafficLightColour = "green";
                            } else if (t.getColour() == 0) {
                                trafficLightColour = "red";
                            }
                            System.out.println("Traffic light at " + t.getLocation() + " turned " + trafficLightColour);
                            t.colourTimer();
                        }
                    }
                    t.colourTimer();
                    break;
                case "4-Way intersection":
                    if (t.getChangedColourTimer() == 0) {
                        if (trafficLight4Way > 50) {
                            t.setLightCycle4Way();
                            if (t.getLightCycle() == 1) {
                                if (t.getTrafficLightNumber() == 3) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 1) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 4) {
                                    t.setRed();
                                }
                            } else if (t.getLightCycle() == 2) {
                                if (t.getTrafficLightNumber() == 3) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 1) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 4) {
                                    t.setRed();
                                }
                            } else if (t.getLightCycle() == 3) {
                                if (t.getTrafficLightNumber() == 3) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 1) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 4) {
                                    t.setRed();
                                }
                            } else if (t.getLightCycle() == 4) {
                                if (t.getTrafficLightNumber() == 3) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 1) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 4) {
                                    t.setGreen();
                                }
                            } else if (t.getLightCycle() == 5) {
                                if (t.getTrafficLightNumber() == 3) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 1) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 4) {
                                    t.setGreen();
                                }
                            } else if (t.getLightCycle() == 6) {
                                if (t.getTrafficLightNumber() == 3) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 1) {
                                    t.setRed();
                                } else if (t.getTrafficLightNumber() == 2) {
                                    t.setGreen();
                                } else if (t.getTrafficLightNumber() == 4) {
                                    t.setRed();
                                }
                            }
                            if (t.getColour() == 1) {
                                trafficLightColour = "green";
                            } else if (t.getColour() == 0) {
                                trafficLightColour = "red";
                            }
                            System.out.println("Traffic light at " + t.getLocation() + " turned " + trafficLightColour);
                            t.colourTimer();
                        }
                    }
                    t.colourTimer();
                    break;
            }
        }
    }

    private void stopVehicleStraightTrafficLight(int roadLength, Vehicle v, TrafficLight t) {
        if (t.getRoadLocation() + map == v.getLocation()) {
            if (roadLength - v.getRoadLocation() + v.getSpeed() <= 2) {
                if (t.getColour() >= 1) {
                    accelerateVehicle = false;
                    v.stopVehicle();
                }
            } else if (roadLength - v.getRoadLocation() <= 5) {
                if (t.getColour() > 1) {
                    accelerateVehicle = false;
                    v.decelerateVehicle();
                }

            }
        }
    }

    private void decelerateTrafficLight(Vehicle v, double nextRoadLocation, int i, int i2, int i3) {
        if (v.getRoadLocation() >= i && v.getRoadLocation() <= i2) {
            if (nextRoadLocation < i3) {
                if (v.getSpeed() > 2) {
                    v.decelerateVehicle();
                }
                v.moveVehicleRoadLocation();
                accelerateVehicle = false;
            } else if (nextRoadLocation >= i3) {
                v.setRoadLocation(i3);
                v.stopVehicle();
                accelerateVehicle = false;
            }
        }
    }

    private void getDirection(int min, int max, Random rand, Vehicle v) {
        int cd;
        cd = rand.nextInt(((max - min) + 1)) + 1;
        if (v.getChosenDirection() == 0) {
            v.setChosenDirection(cd);
        }
    }


    private void checkVehicleInFront(Vehicle v, double nextRoadLocation, Road r, Vehicle v2) {
        if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= 1) {
            v.setSpeed(0);
            System.out.println(v.getName() + " " + v.getId() + " has stopped due to a car in front " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation());
            accelerateVehicle = false;
        } else if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= 4) {
            if (v.getId() != v2.getId() && v.getLocation() == v2.getLocation()) {
                if (v.getSpeed() > 1) {
                    v.setSpeed(v.getSpeed() - 1);
                    v.setRoadLocation(nextRoadLocation);
                    accelerateVehicle = false;
                }
            }
        }
    }

    private void setRoadLocation(int roadLength, Vehicle v, int nextLocation) {
        boolean exitMap = true;
        double nextRoadLocation = v.getRoadLocation() + v.getSpeed();
        if (nextRoadLocation < roadLength) {
            v.setRoadLocation(nextRoadLocation);
        } else if (nextRoadLocation >= roadLength) {
            for (Road r2 : roadArrayList) {
                if (r2.getLocation() == v.getLocation() + nextLocation) {// place nextLocation over map
                    v.setLocation(v.getLocation() + nextLocation);// for loop check for road pieces at new place, if none and code to turn around
                    v.setRoadLocation(nextRoadLocation - roadLength); // set based on mps+ roadLocation - roadLength
                    exitMap = false;
                }
            }
            if (exitMap) {
                System.out.println(v.getName() + " " + v.getId() + " left the map (road finished)");
                v.setLocation(100);
                v.setRoadLocation(1000);
            }
        }
    }

    private void enterTopMap(char roadSide, int roadLocation) { // add code to show direction cars are facing
        checkMapLocation = true;
        char roadDirection = 'd';
        for (int topMap : topMap) { // search through top of map
            if (topMap > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (topMap == road.getLocation()) {
                        switch (road.getName()) {
                            case "Straight":
                                if (road.getOrientation() == 1) {
                                    checkVehicleList(roadSide, roadLocation, topMap, roadDirection);
                                }
                                break;
                            case "4-way intersection":
                                checkVehicleList(roadSide, roadLocation, topMap, roadDirection);
                                break;
                            case "2-Way intersection":
                                if (road.getOrientation() == 2 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                    checkVehicleList(roadSide, roadLocation, topMap, roadDirection);
                                }

                                break;
                        }
                        // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                        // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                    }
                }
            }
        }
    }

    private void enterBottomMap(char roadSide, int roadLocation) {
        checkMapLocation = true;
        char roadDirection = 'u';
        for (int bottomMap : bottomMap) { // search through top of map
            if (bottomMap > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    if (bottomMap == road.getLocation()) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        switch (road.getName()) {
                            case "Straight":
                                if (road.getOrientation() == 1) {
                                    checkVehicleList(roadSide, roadLocation, bottomMap, roadDirection);
                                }

                                break;
                            case "4-way intersection":
                                checkVehicleList(roadSide, roadLocation, bottomMap, roadDirection);
                                break;
                            case "2-Way intersection":
                                if (road.getOrientation() == 1 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                    checkVehicleList(roadSide, roadLocation, bottomMap, roadDirection);
                                }
                                break;
                        }
                        // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                        // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                    }
                }
            }
        }
    }

    private void enterLeftMap(char roadSide, int roadLocation) {
        char roadDirection = 'r';
        checkMapLocation = true;
        for (int i : leftMap) { // search through top of map
            if (i > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    for (int tMap : leftMap) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        if (tMap == road.getLocation()) {
                            switch (road.getName()) {
                                case "Straight":
                                    if (road.getOrientation() == 2) {
                                        checkVehicleList(roadSide, roadLocation, i, roadDirection);
                                    }

                                    break;
                                case "4-way intersection":
                                    checkVehicleList(roadSide, roadLocation, i, roadDirection);
                                    break;
                                case "2-Way intersection":
                                    if (road.getOrientation() == 1 || road.getOrientation() == 3 || road.getOrientation() == 4) {
                                        checkVehicleList(roadSide, roadLocation, i, roadDirection);
                                    }

                                    break;
                            }
                            // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                            // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                        }
                    }
                }
            }
        }
    }

    private void enterRightMap(char roadSide, int roadLocation) {
        checkMapLocation = true;
        char roadDirection = 'l';
        for (int i : rightMap) { // search through top of map
            if (i > 0) { // select a piece that has road on it --- add to check for orientation / entrances of road pieces
                for (Road road : roadArrayList) {
                    for (int tMap : rightMap) { // TOP OF MAP - repeat code for all 4 sides road.getName().equals("4-way intersection")
                        if (tMap == road.getLocation()) {
                            switch (road.getName()) {
                                case "Straight":
                                    if (road.getOrientation() == 2) {
                                        checkVehicleList(roadSide, roadLocation, i, roadDirection);
                                    }

                                    break;
                                case "4-way intersection":
                                    checkVehicleList(roadSide, roadLocation, i, roadDirection);
                                    break;
                                case "2-Way intersection":
                                    if (road.getOrientation() == 1 || road.getOrientation() == 2 || road.getOrientation() == 4) {
                                        checkVehicleList(roadSide, roadLocation, i, roadDirection);
                                    }

                                    break;
                            }
                            // add code for 3way intersection - first fix up map making , orientation, and options for redundant orientations
                            // add code for car class to have direction (char u(up) d (down) l(left) r(right), use to move cars
                        }
                    }
                }
            }
        }
    }


    private void checkVehicleList(char roadSide, int roadLocation, int i, char roadDirection) {

        for (Vehicle v : vehiclesArrayList) {
            if (i == v.getLocation() && v.getRoadLocation() < 4 + v.getLength() && v.getRoadSide() == roadSide) {
                checkMapLocation = false;
                break;
            }
        }
        if (checkMapLocation) {
            addVehicle(roadSide, roadLocation, i, roadDirection);

            checkMapLocation = true;
        }
        checkMapLocation = true;
    }

    private void addVehicle(char roadSide, int roadLocation, int i, char roadDirection) {
        Collections.shuffle(vehiclesArrayList);
        int enterMap = 0;
        for (Vehicle vehicle : vehiclesArrayList) {
            if (enterMap == 0 && vehicle.getRoadDirection() == 'u') {
                vehicle.setLocation(i);
                vehicle.setRoadSide(roadSide);
                vehicle.setRoadLocation(roadLocation);
                vehicle.setRoadDirection(roadDirection);
                System.out.println("A car enter the map at " + vehicle.getLocation() + " location");
                enterMap = 1;
            }
        }
        //carsOnMap++;
    }
}