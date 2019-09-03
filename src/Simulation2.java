import java.util.ArrayList;
import java.util.TimerTask;

public class Simulation2 extends TimerTask {

    private final int cars;
    private final int map;
    private final ArrayList<Road> roadArrayList;
    private final ArrayList<Vehicle> vehiclesArrayList;
    private final ArrayList<TrafficLight> trafficLightArrayList;
    private boolean checkMapLocation = true;
    private int carsOnMap = 0;
    private int[] topMap;
    private int[] bottomMap;
    private int[] leftMap;
    private int[] rightMap;
    private int count=0;

    Simulation2(int cars, ArrayList<Road> roadArrayList, ArrayList<TrafficLight> trafficLightArrayList, int[] topMap, int[] bottomMap, int[] leftMap, int[] rightMap, ArrayList<Vehicle> vehiclesArrayList, int map) {
        this.cars = cars;
        this.roadArrayList = roadArrayList;
        this.trafficLightArrayList = trafficLightArrayList;
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
        int roadLength = 40;
        int carOfMap = map * map + 1;

        double nextRoadLocation;
        double mps;
        boolean accelerateVehicle = true;
        boolean exitMap = true;
/*        if(count < 10){
            for(TrafficLight trafficLight : trafficLightArrayList){
                trafficLight.setColour(1);
            }
        }
        else if (count < 15){
            for(TrafficLight trafficLight : trafficLightArrayList){
                trafficLight.setColour(2);
            }
        }
        else if (count < 20){
            for(TrafficLight trafficLight : trafficLightArrayList){
                trafficLight.setColour(5);
            }
        }
        else if (count < 25){
            for(TrafficLight trafficLight : trafficLightArrayList){
                trafficLight.setColour(1);
                count = 0;
            }
        }
        count++;
*/
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

                for (TrafficLight trafficLight : trafficLightArrayList) {
                    if (trafficLight.getLocation() == v.getLocation()) {
                        if (trafficLight.getRoadLocation() == 'b') {
                            if (roadLength - v.getRoadLocation() <= 20) {
                                if (trafficLight.getColour() == 2 || trafficLight.getColour() == 3 || trafficLight.getColour() == 4 || trafficLight.getColour() == 5) {
                                    decelerateTrafficLights(nextRoadLocation, v);
                                    v.setRoadLocation(nextRoadLocation);
                                    System.out.println("1 - "+v.getType() + " " + v.getId() + " is slowing down for a traffic light, Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed());
                                    accelerateVehicle = false;
                                }
                            } else if (40 - v.getRoadLocation() <= 1) {
                                if (trafficLight.getColour() == 2 || trafficLight.getColour() == 3 || trafficLight.getColour() == 4 || trafficLight.getColour() == 5) {
                                    accelerateVehicle = false;
                                    v.setSpeed(0);
                                    v.setRoadLocation(nextRoadLocation);
                                    System.out.println("2 - " + v.getType() + " " + v.getId() + " has stopped for a traffic light, Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed());
                                }

                            }
                        } else if (trafficLight.getRoadLocation() == 't') {
                            System.out.println("Error");
                            if (trafficLight.getRoadLocation() + map == v.getLocation()) {
                                if (40 - v.getRoadLocation() >= 8) {
                                    if (trafficLight.getColour() == 2 || trafficLight.getColour() == 3 || trafficLight.getColour() == 4) {
                                        decelerateTrafficLights(nextRoadLocation, v);
                                        accelerateVehicle = false;
                                    }
                                }
                            } else if (20 - v.getRoadLocation() >= 2) {
                                if (trafficLight.getColour() == 2 || trafficLight.getColour() == 3 || trafficLight.getColour() == 4 || trafficLight.getColour() == 5) {
                                    accelerateVehicle = false;
                                    v.setSpeed(0);
                                }

                            }
                        }
                    }
                }

                if (v.getRoadDirection() == 'd') {
                    for (Road r : roadArrayList) { // search roads
                        if (r.getLocation() == v.getLocation()) {
                            if (r.getName().equals("Straight")) {
                                if (r.getOrientation() == 1 || r.getOrientation() == 2){
                                    accelerateVehicle = checkVehicleInFront(nextRoadLocation, accelerateVehicle, v, r);
                                }

                            }
                            else if (r.getName().equals("2-Way intersection")) {
                                if (r.getOrientation() == 2 || r.getOrientation() == 3 || r.getOrientation() == 4 ){
                                    if (v.getRoadLocation()+mps < 19){
                                        accelerateVehicle = checkVehicleInFront(nextRoadLocation, accelerateVehicle, v, r);
                                    }
                                    else if (v.getRoadLocation()+mps > 19){


                                        //####################################################################
                                        // assighn direction based on orientation - use random number generator
                                        // add code for of 2-way certain orientation and after getlocation >21(maybe+mps) ---accelerateVehicle = checkVehicleInFront(nextRoadLocation, accelerateVehicle, v, r);
                                    }
                                }

                            }
                            if (accelerateVehicle) {
                                accelerateVehicle(v);
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
                                        System.out.println("3 - " + v.getType() + " " + v.getId() + " left the map (road finished)");
                                        v.setLocation(carOfMap);
                                        v.setRoadLocation(0);
                                    }
                                }

                                System.out.println("4 - " + v.getType() + " " + v.getId() + " on a " + r.getName() + " - RoadLocation " + v.getRoadLocation() + " - Location: " + v.getLocation() + " - Speed: " + v.getSpeed());
                            }
                            accelerateVehicle = true;
                        }
                    }

                }

            }
        }
    }

    private boolean checkVehicleInFront(double nextRoadLocation, boolean accelerateVehicle, Vehicle v, Road r) {
        for (Vehicle v2 : vehiclesArrayList) {
            if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation()) {
                int slowingDownDistance = 10;
                int stoppingDistance = 4;
                if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= stoppingDistance) {
                    v.setSpeed(0);
                    System.out.println(v.getType() + " " + v.getId() + " has stopped due to a car in front " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation());
                    accelerateVehicle = false;
                } else if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= slowingDownDistance) {
                    if (v.getId() != v2.getId() && v.getLocation() == v2.getLocation()) {
                        // slow down car
                        decelerateVehicle(v);
                        v.setRoadLocation(nextRoadLocation);
                        if (v.getSpeed() > 0) {
                            System.out.println(v.getType() + " " + v.getId() + " decelerated on a " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation() + " at speed: " + v.getSpeed());
                            accelerateVehicle = false;
                        } else {
                            System.out.println(v.getType() + " " + v.getId() + " has stopped " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation());
                        }

                    }
                }

            }
        }
        return accelerateVehicle;
    }


    private void decelerateTrafficLights(double nextRoadLocation, Vehicle v) {
        if (v.getSpeed() >= 35) {
            v.setSpeed(v.getSpeed() - 15);
            v.setRoadLocation(v.getRoadLocation() + (v.getSpeed() / 3.6));
            System.out.println(v.getType() + " " + v.getId() + " is slowing down for a traffic light, Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed());
        } else if (v.getSpeed() >= 25) {
            v.setSpeed(v.getSpeed() - 10);
            v.setRoadLocation(nextRoadLocation);
            System.out.println(v.getType() + " " + v.getId() + " is slowing down for a traffic light, Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed());
        }
        else if (v.getSpeed() >= 15) {
            v.setSpeed(v.getSpeed() - 6);
            v.setRoadLocation(nextRoadLocation);
            System.out.println(v.getType() + " " + v.getId() + " is slowing down for a traffic light, Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed());
        }
        else {
            v.setSpeed(0);
            v.setRoadLocation(nextRoadLocation);
            System.out.println(v.getType() + " " + v.getId() + " is slowing down for a traffic light, Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed());

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
            v.setSpeed(v.getSpeed() - 10);
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
            if (i == v.getLocation() && v.getRoadLocation() < 10 && v.getRoadSide() == roadSide) {
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
