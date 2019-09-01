import java.util.ArrayList;
import java.util.TimerTask;
import java.lang.*;

public class Simulation extends TimerTask {

    private final int cars;
    private final int map;
    private final ArrayList<Road> roadArrayList;
    private final ArrayList<Vehicle> vehiclesArrayList;
    private boolean checkMapLocation = true;
    private int carsOnMap = 0;
    private int[] topMap;
    private int[] bottomMap;
    private int[] leftMap;
    private int[] rightMap;


    Simulation(int cars, ArrayList<Road> roadArrayList, int[] topMap, int[] bottomMap, int[] leftMap, int[] rightMap, ArrayList<Vehicle> vehiclesArrayList, int map) {
        this.cars = cars;
        this.roadArrayList = roadArrayList;
        this.topMap = topMap;
        this.bottomMap = bottomMap;
        this.leftMap = leftMap;
        this.rightMap = rightMap;
        this.vehiclesArrayList = vehiclesArrayList;
        this.map = map;
    }

    @Override
    public void run() {
        char roadSideR = 'r';
        char roadSideL = 'l';
        int roadLocation = 1;
        int carOfMap = map * map + 1;
        double nextRoadLocation;
        double mps;
        boolean vehicleNotInFront = true;
        boolean exitMap = true;

        if (cars > 0) {
            enterTopMap(roadSideR, roadLocation); // check if cars can and enter top of map
            enterBottomMap(roadSideL, roadLocation);
            enterLeftMap(roadSideL, roadLocation);
            enterRightMap(roadSideR, roadLocation);
        }
        for (Vehicle v : vehiclesArrayList) {
            int nextLocationDown = v.getLocation() + map;
            mps = v.getSpeed() / 3.6;
            nextRoadLocation = v.getRoadLocation() + mps;
            if (v.getLocation() > 0 && v.getLocation() < carOfMap) {
                if (v.getRoadDirection() == 'd') {
                    for (Road r : roadArrayList) { // search roads
                        if (r.getLocation() == v.getLocation()) {
                            if (r.getName().equals("Straight") && r.getOrientation() == 1 || r.getOrientation() == 2) {
                                for (Vehicle v2 : vehiclesArrayList) {
                                    if (v.getRoadLocation() < v2.getRoadLocation()) {
                                        int stoppingDistance = 36;
                                        if (v2.getRoadLocation() - nextRoadLocation <= stoppingDistance) {
                                            if (v.getId() != v2.getId() && v.getLocation() == v2.getLocation()) {
                                                // slow down car
                                                decelerateVehicle(v);
                                                v.setRoadLocation(nextRoadLocation);
                                                System.out.println("Car " + v.getId() + " decelerated on a " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation() + " at speed: " + v.getSpeed());
                                                vehicleNotInFront = false;
                                            }
                                        }
                                    }
                                }
                                if (vehicleNotInFront) {
                                    accelerateVehicle(v);
                                    int roadLength = 20;
                                    if (v.getRoadLocation() + mps < roadLength) {
                                        v.setRoadLocation(v.getRoadLocation() + mps);
                                    } else if (v.getRoadLocation() + mps >= roadLength) {
                                        for (Road r2 : roadArrayList) {
                                            if (r2.getLocation() == nextLocationDown) {
                                                v.setLocation(nextLocationDown);// for loop check for road pieces at new place, if none and code to turn around
                                                v.setRoadLocation(v.getRoadLocation() + mps - roadLength); // set based on mps+ roadLocation - roadLength
                                                exitMap = false;
                                            }
                                        }
                                        if (exitMap) {
                                            System.out.println("Car " + v.getId() + " left the map (road finished)");
                                            v.setLocation(carOfMap);
                                        }
                                    }
                                    System.out.println("Car " + v.getId() + " on a " + r.getName() + " - RoadLocation " + v.getRoadLocation() + " - Location: " + v.getLocation() + " - Speed: " + v.getSpeed());
                                }

                            }
                        }

                    }
                }
            }
        }
    }

    private void accelerateVehicle(Vehicle v) {
        if (v.getSpeed() < 50) {
            if (v.getSpeed() <= 43) {
                int newSpeed = v.getSpeed() + 7;
                v.setSpeed(newSpeed);
            } else {
                v.setSpeed(50);
            }
        }
    }

    private void decelerateVehicle(Vehicle v) {
        if (v.getSpeed() >= 20) {
            v.setSpeed(v.getSpeed() - 20);
        } else {
            v.setSpeed(0);
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
                                if (road.getOrientation() == 1 || road.getOrientation() == 2) {
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
                                if (road.getOrientation() == 1 || road.getOrientation() == 2) {
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
                                    if (road.getOrientation() == 3 || road.getOrientation() == 4) {
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
                                    if (road.getOrientation() == 3 || road.getOrientation() == 4) {
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
            if (i == v.getLocation() && v.getRoadLocation() < 37 && v.getRoadSide() == roadSide) {
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
        for (Vehicle vehicle : vehiclesArrayList) {
            if (vehicle.getId() == carsOnMap) {
                vehicle.setLocation(i);
                vehicle.setRoadSide(roadSide);
                vehicle.setRoadLocation(roadLocation);
                vehicle.setRoadDirection(roadDirection);
                System.out.println("A car enter the map at " + vehicle.getLocation() + " location");
            }
        }
        carsOnMap++;
    }
}
