import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class TrafficSimulator {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int map = 4; // change to get user input between 1 - 10 for map size
        int mapSize = map*map;
        int currentSize = 0;
        int userInput;
        int numberOfCars;
        int[] currentMap = new int[mapSize];
        int[] topMap = new int[map + 1];
        int[] bottomMap = new int[map + 1];
        int[] leftMap = new int[map + 1];
        int[] rightMap = new int[map + 1];
        int currentMapSize = 0;
        boolean run = true;
        String userInputMessage = "Please select from a number from the list below.\n(1) Load Map \n(2) Save Map \n(3) Create / edit map  \n(4) Run Simulation \n(5) Quite Application";

        ArrayList<Road> roadArrayList = new ArrayList<>();
        ArrayList<Vehicle> vehiclesArrayList = new ArrayList<>();
        createEmptyMap(mapSize, currentSize, currentMap);

        System.out.println("Welcome to the traffic simulator\n");
        Scanner scanner = new Scanner(System.in);
        userInput = getUserInput(scanner, userInputMessage);
        while (run)
            if (userInput == (1)) {
                System.out.println("Load Map");
                roadArrayList = loadRoad.invoke();
                userInput = getUserInput(scanner, userInputMessage);
            } else if (userInput == (2)) {
                System.out.println("Save Map");
                saveRoad(roadArrayList);
                userInput = getUserInput(scanner, userInputMessage);
            } else if (userInput == (3)) {
                System.out.println("Create / edit map");
                userInput = editMap(userInput, scanner, mapSize, currentMap, roadArrayList, map);

            } else if (userInput == (4)) {
                System.out.println("Run Simulation");
                //String inputMessage = "Please enter the number of cars you would like in this simulation"; // expand later to get input for other vehicles and add to vehicle list
                //numberOfCars = getUserInput(scanner, inputMessage);
                numberOfCars = 20;
                // add method to add cars to vehicleList - pass to simulation
                for (int i = 0; i < numberOfCars; ) {
                    Vehicle car = new Vehicle(1, 0, 0, 'n', 0, i, 'u');
                    vehiclesArrayList.add(car);
                    i++;
                }

                getTopMap(map, roadArrayList, topMap, currentMapSize);
                getBottomMap(map, roadArrayList, bottomMap, currentMapSize);
                getLeftMap(map, roadArrayList, leftMap, currentMapSize);
                getRightMap(map, roadArrayList, rightMap, currentMapSize);

                Timer timer = new Timer();
                timer.schedule(new Simulation(numberOfCars, currentMap, roadArrayList, map, topMap, bottomMap, leftMap, rightMap, vehiclesArrayList), 0, 5000);


                run = false;

            } else if (userInput == (5)) {
                System.out.println("Quite Application");
                run = false;
            } else {
                userInput = getUserInput(scanner, userInputMessage);
            }
    }

    private static void getRightMap(int map, ArrayList<Road> roadArrayList, int[] rightMap, int currentMapSize) {
        int mapSize = map * map;
        for (int i = map; i <= mapSize; ) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    rightMap[currentMapSize] = i;
                }
            }
            i = i + map;
        }
    }

    private static void getLeftMap(int map, ArrayList<Road> roadArrayList, int[] leftMap, int currentMapSize) {
        int mapSize = map * map;
        for (int i = 1; i <= mapSize; ) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    leftMap[currentMapSize] = i;
                }
            }
            i = i + map;
        }
    }

    private static void getBottomMap(int map, ArrayList<Road> roadArrayList, int[] topMap, int currentMapSize) {
        int mapSubtraction = map * map;
        int mapStop = mapSubtraction - map;
        for (int i = mapSubtraction; i > mapStop; i--, currentMapSize++) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    topMap[currentMapSize] = i;
                }
            }
        }
    }

    private static void getTopMap(int map, ArrayList<Road> roadArrayList, int[] topMap, int currentMapSize) {
        for (int i = 1; i <= map; i++, currentMapSize++) {
            for (Road v : roadArrayList) {
                if (v.getLocation() == i) {
                    topMap[currentMapSize] = i;
                }
            }
        }
    }


    private static void printMap(int[] currentMap, ArrayList<Road> roadArrayList, int map) {
        boolean addRoad = true;
        int displayMapSize = 1;
        for (int v : currentMap) {
            for (Road i : roadArrayList){
                if (i.getLocation() == v ){
                    switch (i.getName()) {
                        case "Straight":
                            if (i.getOrientation() == 1 || i.getOrientation() == 2) {
                                System.out.print("|" + ", ");
                                addRoad = false;
                            } else if (i.getOrientation() == 3 || i.getOrientation() == 4) {
                                System.out.print("_" + ", ");
                                addRoad = false;
                            }
                            break;
                        case "4-way intersection":
                            if (i.getOrientation() == 1 || i.getOrientation() == 2 || i.getOrientation() == 3 || i.getOrientation() == 4) {
                                System.out.print("+" + ", ");
                                addRoad = false;
                            }
                            break;
                        case "2-Way intersection":
                            if (i.getOrientation() == 1) {
                                System.out.print("T" + ", ");
                                addRoad = false;
                            } else if (i.getOrientation() == 2) {
                                System.out.print("|͟" + ", ");
                                addRoad = false;
                            } else if (i.getOrientation() == 3) {
                                System.out.print("~|" + ", ");
                                addRoad = false;
                            } else if (i.getOrientation() == 4) {
                                System.out.print("|~" + ", ");
                                addRoad = false;
                            }
                            break;
                    }
                }
            }
            if (addRoad) {
                System.out.print(v + ", ");
            }
            if (displayMapSize == map) {
                System.out.println();
                displayMapSize = 0;
            }
            addRoad = true;
            displayMapSize++;
        }
        System.out.println();
    }

    private static void createEmptyMap(int mapSize, int currentSize, int[] currentMap) {
        for (int i = 1; i < mapSize + 1; ) {
            currentMap[currentSize] = i;
            i = i + 1;
            currentSize += 1;
        }
    }



    private static void saveRoad(ArrayList<Road> roadArrayList) throws IOException {
        FileOutputStream fout = new FileOutputStream("map.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(roadArrayList);
        fout.close();
    }

    private static int editMap(int userInput, Scanner scanner, int mapSize, int[] currentMap, ArrayList<Road> roadArrayList, int map) {
        int roadInput;
        String roadName;
        int roadOrientation;
        boolean selectRoad = true;
        String roadDisplayMessage = "Please select from a number from the list below to add a piece of road." +
                "\n (1) Strait " +
                "\n (2) 4-way intersection " +
                "\n (3) 2-way intersection " +
                "\n (4) Print Map " +
                "\n (5) Quite ";
        roadInput = getUserInput(scanner, roadDisplayMessage);
        while (selectRoad)
            if (roadInput == (1)){
                roadName = "Straight";
                System.out.println("You Selected Straight");
                roadOrientation = getOrientationRoad(scanner);

                int positionInput = getPositionRoad(scanner, mapSize, currentMap, roadArrayList, map);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                roadArrayList.add(road);
                selectRoad = false;

            } else if (roadInput == (2)){
                roadName = "4-way intersection";
                System.out.println("You Selected 4 - way intersection");
                roadOrientation = getOrientationRoad(scanner);
                int positionInput = getPositionRoad(scanner, mapSize, currentMap, roadArrayList, map);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                roadArrayList.add(road);
                selectRoad = false;

            } else if (roadInput == (3)){
                roadName = "2-Way intersection";
                System.out.println("You Selected 2 - way intersection");
                roadOrientation = getOrientationRoad(scanner);
                int positionInput = getPositionRoad(scanner, mapSize, currentMap, roadArrayList, map);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                roadArrayList.add(road);
                selectRoad = false;

            }else if (roadInput == (4)){
                System.out.println("Print Map");
                printMap(currentMap, roadArrayList, map);
                selectRoad = false;
                userInput = getUserInput(scanner);

            } else if (roadInput == (5)){
                System.out.println("Quite");
                selectRoad = false;
                userInput = getUserInput(scanner);

            } else {
                roadInput = getUserInput(scanner, roadDisplayMessage);
            }
        return userInput;
    }

    private static int getPositionRoad(Scanner scanner, int mapSize, int[] currentMap, ArrayList<Road> roadArrayList, int map) {
        int positionInput;
        boolean positionRun = true;
        positionInput = getUserInputPosition(scanner, currentMap, roadArrayList, map);
        while (positionRun)
            if (positionInput < mapSize){
                positionRun = false;

            } else {
                positionInput = getUserInputPosition(scanner, currentMap, roadArrayList, map);
            }
        return positionInput;
    }

    private static int getOrientationRoad(Scanner scanner) {
        boolean orientationRun = true;
        int orientationInput;
        String orientationDisplayMessage = "Please select the orientation you would like the piece \n(1) Straight, \n(2) Upside Down \n(3) Left \n(4) Right";
        orientationInput = getUserInput(scanner, orientationDisplayMessage);
        while (orientationRun)
            if (orientationInput == 1){
                System.out.println("Straight Selected");
                orientationRun = false;
            }else if (orientationInput == (2)){
                System.out.println("Upside Down Selected");
                orientationRun = false;
            } else if (orientationInput == (3)){
                System.out.println("Left Selected");
                orientationRun = false;
            }else if (orientationInput == (4)){
                System.out.println("Right Selected");
                orientationRun = false;
            } else {
                orientationInput = getUserInput(scanner, orientationDisplayMessage);

            }
        return orientationInput;
    }

    private static int getUserInput(Scanner userInput) {
        int userIntroChoice;
        String errorText = "Please select from a number from the list below.\n(1) Load Map \n(2) Save Map \n(3) Create / edit map  \n(4) Run Simulation \n(5) Quite Application";
        do {
            System.out.println(errorText);
            while (!userInput.hasNextInt()) {
                System.out.println(errorText);
                userInput.next();
            }
            userIntroChoice = userInput.nextInt();
        } while (userIntroChoice <= 0);
        return userIntroChoice;
    }

    private static int getUserInput(Scanner userInput, String errorText) {
        int userIntroChoice;
        do {
            System.out.println(errorText);
            while (!userInput.hasNextInt()) {
                System.out.println(errorText);
                userInput.next();
            }
            userIntroChoice = userInput.nextInt();
        } while (userIntroChoice <= 0);
        return userIntroChoice;
    }

    private static int getUserInputPosition(Scanner scanner, int[] currentMap, ArrayList<Road> roadArrayList, int map) {
        int userInputRoad;

        do {
            System.out.println("Please select where you would like to place the road piece by entering the number corresponding with the spot below");
            printMap(currentMap, roadArrayList, map);

            while (!scanner.hasNextInt()) {
                System.out.println("Please select where you would like to place the road piece by entering the number corresponding with the spot below");
                printMap(currentMap, roadArrayList, map);
                scanner.next();
            }
            userInputRoad = scanner.nextInt();
        } while (userInputRoad <= 0);
        return userInputRoad;
    }

    private static class loadRoad { // crashes program if map.txt not found
        private static ArrayList<Road> invoke() throws IOException, ClassNotFoundException {
            ArrayList<Road> roadArrayList;
            FileInputStream readRoad = new FileInputStream("map.txt");
            ObjectInputStream ois = new ObjectInputStream(readRoad);
            roadArrayList = (ArrayList<Road>) ois.readObject();
            readRoad.close();
            return roadArrayList;
        }
    }
}

