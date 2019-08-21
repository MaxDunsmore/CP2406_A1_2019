import java.text.DecimalFormat;
import java.util.Scanner;

public class TrafficSimulator {
    public static void main(String[] args) {
        int mapSize = 100;
        int trackForLoop = mapSize/10; // rename
        int currentSize = 0;
        int displayMapSize = 0;

        double[] currentMap = new double[mapSize+1];
        DecimalFormat numberFormat = new DecimalFormat("#.0");
        createEmptyMap(trackForLoop, currentSize, currentMap);


        displayIntroduction(mapSize, trackForLoop, displayMapSize, currentMap, numberFormat);
    }

    private static void printCurrentMap(int trackForLoop, int displayMapSize, double[] currentMap, DecimalFormat numberFormat) {
        for (double v : currentMap) {
            System.out.print(numberFormat.format(v) + ", ");
            if (displayMapSize == trackForLoop) {
                System.out.println();
                displayMapSize = 0;
            }
            displayMapSize++;
        }
    }

    private static void createEmptyMap(int trackForLoop, int currentSize, double[] currentMap) {
        for (double i = 0; i<trackForLoop;){
            currentMap[currentSize] = i;
            i = i + .1;
            currentSize += 1;
        }
    }

    private static void displayIntroduction(int mapSize, int trackForLoop, int displayMapSize, double[] currentMap, DecimalFormat numberFormat) {
        boolean run = true;
        int userInput;


        System.out.println("Welcome to the traffic simulator\n");
        Scanner scanner = new Scanner(System.in);
        userInput = getUserInputIntro(scanner);
        while (run)
            if (userInput == (1)){
                System.out.println("Load Map");
                userInput = getUserInputIntro(scanner);
        } else if (userInput == (2)){
                System.out.println("Save Map");
                userInput = getUserInputIntro(scanner);
        } else if (userInput == (3)){
                System.out.println("Create / edit map");
                userInput = editMap(userInput, scanner, mapSize, trackForLoop, displayMapSize, currentMap, numberFormat );

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

    private static int editMap(int userInput, Scanner scanner, int mapSize,int trackForLoop, int displayMapSize, double[] currentMap, DecimalFormat numberFormat) {

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

                double positionInput = getPositionRoad(scanner, mapSize, trackForLoop, displayMapSize, currentMap, numberFormat);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                // add code to add roads to road ArrayList of roads

                selectRoad = false;
                roadInput = getUserInputRoad(scanner);

            } else if (roadInput == (2)){
                roadName = "4-way intersection";
                System.out.println("You Selected 4 - way intersection");
                roadOrientation = getOrientationRoad(scanner);
                double positionInput = getPositionRoad(scanner, mapSize, trackForLoop, displayMapSize, currentMap, numberFormat);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                selectRoad = false;
                userInput = getUserInputIntro(scanner);

            } else if (roadInput == (3)){
                roadName = "2-Way intersection";
                System.out.println("You Selected 2 - way intersection");
                roadOrientation = getOrientationRoad(scanner);
                double positionInput = getPositionRoad(scanner, mapSize, trackForLoop, displayMapSize, currentMap, numberFormat);
                Road road = new Road(roadName, roadOrientation, positionInput);
                road.printRoad();
                selectRoad = false;
                userInput = getUserInputIntro(scanner);

            } else if (roadInput == (4)){
                System.out.println("Quite");
                selectRoad = false;
                userInput = getUserInputIntro(scanner);

            }else { // error checking if number is invalid
                roadInput = getUserInputRoad(scanner);

            }
        return userInput;
    }

    private static double getPositionRoad(Scanner scanner, int mapSize, int trackForLoop, int displayMapSize, double[] currentMap, DecimalFormat numberFormat) {
        double positionInput;
        boolean positionRun = true;
        positionInput = getUserInputPosition(scanner, trackForLoop, displayMapSize, currentMap, numberFormat );
        int mapSizeMax = mapSize/10;
        while (positionRun)
            if (positionInput < mapSizeMax){
                positionRun = false;

            } else {
                positionInput = getUserInputPosition(scanner, trackForLoop, displayMapSize, currentMap, numberFormat );
            }
        return positionInput;
    }

    private static double getUserInputPosition(Scanner scanner, int trackForLoop, int displayMapSize, double[] currentMap, DecimalFormat numberFormat) {
        double userInputRoad;

        do {
            System.out.println("Please select where you would like to place the road piece by entering the number corresponding with the spot below");
            printCurrentMap(trackForLoop, displayMapSize, currentMap, numberFormat);

            while (!scanner.hasNextDouble()) {
                System.out.println("Please select where you would like to place the road piece by entering the number corresponding with the spot below");
                printCurrentMap(trackForLoop, displayMapSize, currentMap, numberFormat);
                scanner.next();
            }
            userInputRoad = scanner.nextDouble();
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
                    "\n (4) Quite ");

            while (!userInput.hasNextInt()) {
                System.out.println("Please select from a number from the list below to add a piece of road." +
                        "\n (1) Strait " +
                        "\n (2) 4-way intersection " +
                        "\n (3) 2-way intersection " +
                        "\n (4) Quite ");
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

