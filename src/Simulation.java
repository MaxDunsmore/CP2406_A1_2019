import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Simulation extends TimerTask {

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
    private int count = 0;
    private boolean accelerateVehicle = true;
    Timer timer;

    Simulation(int cars, ArrayList<Road> roadArrayList, ArrayList<TrafficLight> trafficLightArrayList, int[] topMap, int[] bottomMap, int[] leftMap, int[] rightMap, ArrayList<Vehicle> vehiclesArrayList, int map, Timer timer) {
        this.cars = cars;
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
        int carOfMap = map * map + 1;

        Random rand = new Random();
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
            if (v.getLocation() > 0 && v.getLocation() < map+1) {
                double nextRoadLocation = v.getRoadLocation() + v.getSpeed();
                if (v.getRoadDirection() == 'd') { // once down is complete, add code for vehicles moving different directions
                    for (Road r : roadArrayList) {
                        if (r.getLocation() == v.getLocation()) {
                            if (r.getName().equals("Straight")) {
                                if (r.getOrientation() == 1 || r.getOrientation() == 2) {
                                    // check if car is in front
                                    for (Vehicle v2 : vehiclesArrayList) {
                                        if (v.getRoadLocation() < v2.getRoadLocation() && v.getRoadSide() == v2.getRoadSide() && v.getLocation() == v2.getLocation()) {
                                            checkVehicleInFront(v, nextRoadLocation, r, v2);
                                        } else {
                                            // else no cars in front, check for traffic lights
                                            for (TrafficLight t : trafficLightArrayList) {
                                                if (t.getLocation() == v.getLocation()) {
                                                    if (t.getRoadLocation() == 'b' && t.getColour() > 1 ) {
                                                        if (v.getRoadLocation() >= 15 && v.getRoadLocation() <= 20) {
                                                            if (nextRoadLocation < 19) {
                                                                if (v.getSpeed() > 2) {
                                                                    v.setSpeed(v.getSpeed() - 1);
                                                                    v.setRoadLocation(v.getRoadLocation() + v.getSpeed());
                                                                    accelerateVehicle = false;
                                                                } else {

                                                                    v.setRoadLocation(v.getRoadLocation() + v.getSpeed());
                                                                    accelerateVehicle = false;
                                                                }
                                                            } else if (nextRoadLocation >= 19) {
                                                                v.setRoadLocation(19);
                                                                v.setSpeed(0);
                                                                accelerateVehicle = false;
                                                                // set vehicle location to 10

                                                            }
                                                        }

                                                    }else if (t.getRoadLocation() == 't') {
                                                        if (t.getRoadLocation() + map == v.getLocation()) {
                                                            if (roadLength - v.getRoadLocation()+v.getSpeed() <= 2) {
                                                                if (t.getColour() >= 1 ) {
                                                                    // Stop vehicle
                                                                    accelerateVehicle = false;
                                                                    v.setSpeed(0);
                                                                }
                                                            }else if (roadLength - v.getRoadLocation() <= 5) {
                                                                if (t.getColour() > 1 ) {
                                                                    accelerateVehicle = false;
                                                                    // deacelerate vehicle

                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (r.getName().equals("2-Way Intersection")) {
                                for (Vehicle v2 : vehiclesArrayList) {
                                    checkVehicleInFront(v, nextRoadLocation, r, v2);
                                }
                                if (accelerateVehicle) {
                                    if (r.getOrientation() == 2) {
                                        if (v.getLocation() + v.getSpeed() >= 9 && v.getLocation() + v.getSpeed() <= 10) {
                                            // random generator - choose to move piece right or left -
                                            // assighn v,chosenDirection
                                            int cd = rand.nextInt(((2 - 1) + 1)) + 1;
                                            if (v.getChosenDirection() == 0) {
                                                v.setChosenDirection(cd);
                                            }
                                            if (v.getChosenDirection() == 1) {
                                                // move car left
                                                for (Vehicle v2 : vehiclesArrayList) {
                                                    if (v2.getLocation() == v.getLocation()) {
                                                        if (v2.getRoadLocation() + v.getSpeed() >= 7 && v2.getRoadLocation() <= 11 && v2.getRoadDirection() != 'd') {
                                                            // stop vehicle
                                                        }
                                                        else {
                                                            // move vehicle to 11 , set direction to left, set road side to left
                                                        }


                                                    }
                                                }
                                            }
                                        }else if (v.getLocation() + v.getSpeed() >= 5 && v.getLocation() + v.getSpeed() < 9) {
                                            // slow down vehicle
                                        }


                                    }
                                }
                            } else if (r.getOrientation() == 3) {
                                // code for vehile turning right
                            }
                        }
                        // traffic light code
                    }
                }
            }

            if (accelerateVehicle) {
                if (v.getLocation() <= map){
                    if (v.getSpeed() < 5) {
                        v.setSpeed(v.getSpeed() + 1);
                        setRoadLocation(roadLength, carOfMap, v);
                    } else {
                        setRoadLocation(roadLength, carOfMap, v);
                    }
                }



            }
            if (v.getLocation() <=map){
                System.out.println(v.getType() + " " + v.getId() + " Location: " + v.getLocation() + ". Road location: " + v.getRoadLocation() + ". Speed: " + v.getSpeed() * 10);
                accelerateVehicle = true;
            }
            else {
                System.out.println("No cars left on map");
                timer.cancel();
            }

        }
    }


    private void checkVehicleInFront(Vehicle v, double nextRoadLocation, Road r, Vehicle v2) {
        if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= 1) {
            v.setSpeed(0);
            System.out.println(v.getType() + " " + v.getId() + " has stopped due to a car in front " + r.getName() + " at Road Location " + v.getRoadLocation() + " , map Location " + v.getLocation());
            accelerateVehicle = false;
        } else if (v2.getRoadLocation() - v2.getLength() - nextRoadLocation <= 4) {
            if (v.getId() != v2.getId() && v.getLocation() == v2.getLocation()) {
                if (v.getSpeed() > 1) {
                    v.setSpeed(v.getSpeed() - 1);
                    v.setRoadLocation(v.getRoadLocation() + v.getSpeed());
                    accelerateVehicle = false;
                }
            }
        }
    }

    private void setRoadLocation(int roadLength, int carOfMap, Vehicle v) {
        boolean exitMap = true;
        if (v.getRoadLocation() + v.getSpeed() < roadLength) {
            v.setRoadLocation(v.getRoadLocation() + v.getSpeed());
        } else if (v.getRoadLocation() + v.getSpeed() >= roadLength) {
            for (Road r2 : roadArrayList) {
                if (r2.getLocation() == v.getLocation() + map) {
                    v.setLocation(v.getLocation() + map);// for loop check for road pieces at new place, if none and code to turn around
                    v.setRoadLocation(v.getRoadLocation() + v.getSpeed() - roadLength); // set based on mps+ roadLocation - roadLength
                    exitMap = false;
                }
            }
            if (exitMap) {
                System.out.println(v.getType() + " " + v.getId() + " left the map (road finished)");
                v.setLocation(carOfMap);
                v.setRoadLocation(0);
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
            if (i == v.getLocation() && v.getRoadLocation() < 4 && v.getRoadSide() == roadSide) {
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
