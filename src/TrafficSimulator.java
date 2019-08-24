import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TrafficSimulator {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int map = 10; // change to get user input between 1 - 10 for map size
        int mapSize = map*map;
        int currentSize = 0;
        int[] currentMap = new int[mapSize+1];

        ArrayList<Road> roadArrayList = new ArrayList<>();
        Road road = new Road();
        roadArrayList.add(road);
        createEmptyMap(mapSize, currentSize, currentMap);
        displayIntroduction(mapSize, map, currentMap, roadArrayList);
    }

    private static void printMap(int inputMap, int[] currentMap, ArrayList<Road> roadArrayList) {
        boolean addRoad = true;
        int displayMapSize = 0;
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
            if (displayMapSize == inputMap) {
                System.out.println();
                displayMapSize = 0;
            }
            addRoad = true;
            displayMapSize++;
        }
    }

    private static void createEmptyMap(int mapSize, int currentSize, int[] currentMap) {
        for (int i = 0; i<mapSize;){
            currentMap[currentSize] = i;
            i = i + 1;
            currentSize += 1;
        }
    }

    private static void displayIntroduction(int mapSize, int trackForLoop, int[] currentMap, ArrayList<Road> roadArrayList) throws IOException, ClassNotFoundException {
        boolean run = true;
        int userInput;


        System.out.println("Welcome to the traffic simulator\n");
        Scanner scanner = new Scanner(System.in);
        userInput = getUserInputIntro(scanner);
        while (run)
            if (userInput == (1)){
                System.out.println("Load Map");
                roadArrayList = loadRoad.invoke();
                userInput = getUserInputIntro(scanner);
        } else if (userInput == (2)){
                System.out.println("Save Map");
                saveRoad(roadArrayList);
                userInput = getUserInputIntro(scanner);
        } else if (userInput == (3)){
                System.out.println("Create / edit map");
                userInput = editMap(userInput, scanner, mapSize, trackForLoop, currentMap, roadArrayList);

            }  else if (userInput == (4)){
                System.out.println("Run Simulation");
                run = false;
        }else if (userInput == (5)){
                System.out.println("Quite Application");
                run = false;
        }else { // error checking if number is invalid
                userInput = getUserInputIntro(scanner);
            }
    }


    private static void saveRoad(ArrayList<Road> roadArrayList) throws IOException {
        FileOutputStream fout = new FileOutputStream("map.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(roadArrayList);
        fout.close();
    }
    private static class loadRoad {
        private static ArrayList<Road> invoke() throws IOException, ClassNotFoundException {
            ArrayList<Road> roadArrayList;
            FileInputStream readRoad = new FileInputStream("map.txt");
            ObjectInputStream ois = new ObjectInputStream(readRoad);
            roadArrayList = (ArrayList<Road>) ois.readObject();
            readRoad.close();
            return roadArrayList;
        }
    }

    private static int editMap(int userInput, Scanner scanner, int mapSize, int trackForLoop, int[] currentMap,  ArrayList<Road> roadArrayList) {
        int roadInput;
        String roadName;
        int roadOrientation;
        boolean selectRoad = true;
        roadInput = getUserInputRoad(scanner);
        while (selectRoad)
            if (roadInput == (1)){
                roadName = "Straight";
                System.out.println("You Selected Straight");
                roadOrientation = getOrientationRoad(scanner);

                int positionInput = getPositionRoad(scanner, mapSize, trackForLoop, currentMap, roadArrayList);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                roadArrayList.add(road);
                selectRoad = false;

            } else if (roadInput == (2)){
                roadName = "4-way intersection";
                System.out.println("You Selected 4 - way intersection");
                roadOrientation = getOrientationRoad(scanner);
                int positionInput = getPositionRoad(scanner, mapSize, trackForLoop, currentMap, roadArrayList);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                roadArrayList.add(road);
                selectRoad = false;

            } else if (roadInput == (3)){
                roadName = "2-Way intersection";
                System.out.println("You Selected 2 - way intersection");
                roadOrientation = getOrientationRoad(scanner);
                int positionInput = getPositionRoad(scanner, mapSize, trackForLoop, currentMap, roadArrayList);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                roadArrayList.add(road);
                selectRoad = false;

            }else if (roadInput == (4)){
                System.out.println("Print Map");
                printMap(trackForLoop, currentMap, roadArrayList);
                selectRoad = false;
                userInput = getUserInputIntro(scanner);

            } else if (roadInput == (5)){
                System.out.println("Quite");
                selectRoad = false;
                userInput = getUserInputIntro(scanner);

            }else { // error checking if number is invalid
                roadInput = getUserInputRoad(scanner);

            }
        return userInput;
    }

    private static int getPositionRoad(Scanner scanner, int mapSize, int trackForLoop, int[] currentMap, ArrayList<Road> roadArrayList) {
        int positionInput;
        boolean positionRun = true;
        positionInput = getUserInputPosition(scanner, trackForLoop, currentMap, roadArrayList);
        while (positionRun)
            if (positionInput < mapSize){
                positionRun = false;

            } else {
                positionInput = getUserInputPosition(scanner, trackForLoop, currentMap, roadArrayList);
            }
        return positionInput;
    }

    private static int getUserInputPosition(Scanner scanner, int trackForLoop, int[] currentMap, ArrayList<Road> roadArrayList) {
        int userInputRoad;

        do {
            System.out.println("Please select where you would like to place the road piece by entering the number corresponding with the spot below");
            printMap(trackForLoop, currentMap, roadArrayList);

            while (!scanner.hasNextInt()) {
                System.out.println("Please select where you would like to place the road piece by entering the number corresponding with the spot below");
                printMap(trackForLoop, currentMap, roadArrayList);
                scanner.next();
            }
            userInputRoad = scanner.nextInt();
        } while (userInputRoad <= 0 );
        return userInputRoad;
    }

    private static int getOrientationRoad(Scanner scanner) {
        boolean orientationRun = true;
        int orientationInput;
        orientationInput = getUserInputOrientation(scanner);
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
            } else { // error checking if number is invalid
                orientationInput = getUserInputOrientation(scanner);

            }
        return orientationInput;
    }

    private static int getUserInputIntro(Scanner userInput) {
        int userIntroChoice;
        do {
            System.out.println("Please select from a number from the list below.\n(1) Load Map \n(2) Save Map \n(3) Create / edit map  \n(4) Run Simulation \n(5) Quite Application");
            while (!userInput.hasNextInt()) {
                System.out.println("Please select from a number from the list below.\n(1) Load Map \n(2) Save Map \n(3) Create / edit map  \n(4) Run Simulation \n(5) Quite Application");
                userInput.next();
            }
            userIntroChoice = userInput.nextInt();
        } while (userIntroChoice <= 0);
        return userIntroChoice;
    }

    private static int getUserInputRoad(Scanner userInput) {
        int userInputRoad;
        do {
            System.out.println("Please select from a number from the list below to add a piece of road." +
                    "\n (1) Strait " +
                    "\n (2) 4-way intersection " +
                    "\n (3) 2-way intersection " +
                    "\n (4) Print Map " +
                    "\n (5) Quite ");

            while (!userInput.hasNextInt()) {
                System.out.println("Please select from a number from the list below to add a piece of road." +
                        "\n (1) Strait " +
                        "\n (2) 4-way intersection " +
                        "\n (3) 2-way intersection " +
                        "\n (4) Print Map " +
                        "\n (5) Quite ");
                userInput.next();
            }
            userInputRoad = userInput.nextInt();
        } while (userInputRoad <= 0);
        return userInputRoad;
    }

    private static int getUserInputOrientation(Scanner userInput) {
        int userInputOrientationRoad;
        do {
            System.out.println("Please select the orientation you would like the piece \n(1) Straight, \n(2) Upside Down \n(3) Left \n(4) Right");

            while (!userInput.hasNextInt()) {
                System.out.println("Please select the orientation you would like the piece \n(1) Straight, \n(2) Upside Down \n(3) Left \n(4) Right");
                userInput.next();
            }
            userInputOrientationRoad = userInput.nextInt();
        } while (userInputOrientationRoad <= 0);
        return userInputOrientationRoad;
    }

}

