import java.text.DecimalFormat;
import java.util.Scanner;

public class TrafficSimulator {
    public static void main(String[] args) {
        boolean run = true;
        boolean loadMap = true;

        int mapSize = 100;
        int trackForLoop = mapSize/10; // rename
        int currentSize = 0;
        int displayMapSize = 0;

        double[] currentMap = new double[mapSize+1];
        DecimalFormat numberFormat = new DecimalFormat("#.0");

        createEmptyMap(trackForLoop, currentSize, currentMap);
        printCurrentMap(trackForLoop, displayMapSize, currentMap, numberFormat);

        displayIntroduction(run);
    }

    private static void printCurrentMap(int trackForLoop, int displayMapSize, double[] currentMap, DecimalFormat numberFormat) {
        for (int i=0; i<currentMap.length; i+= 1){
            System.out.print(numberFormat.format(currentMap[i]) + ", ");
            if (displayMapSize == trackForLoop){
                System.out.println();
                displayMapSize = 0;
            }
            displayMapSize ++;
        }
    }

    private static void createEmptyMap(int trackForLoop, int currentSize, double[] currentMap) {
        for (double i = 0; i<trackForLoop;){
            i = i + .1;
            currentMap[currentSize] = i;
            currentSize += 1;
        }
    }

    private static void displayIntroduction(boolean run) {
        int userInput;
        int roadInput;
        int orientation;



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
                boolean selectRoad = true;
                roadInput = getUserInputRoad(scanner);
                while (selectRoad)
                    if (roadInput == (1)){
                        System.out.println("You Selected Straight");

                        // add code to chose orientation - move to method

                        selectRoad = false;
                        userInput = getUserInputIntro(scanner);
                    } else if (roadInput == (2)){
                        System.out.println("You Selected 4 - way intersection");
                        selectRoad = false;
                        userInput = getUserInputIntro(scanner);
                    } else if (roadInput == (3)){
                        System.out.println("You Selected 2 - way intersection");
                        selectRoad = false;
                        userInput = getUserInputIntro(scanner);
                    }else { // error checking if number is invalid
                        roadInput = getUserInputRoad(scanner);
                        System.out.println("TESTING " + roadInput);
                    }

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
            System.out.println("Please select from a number from the list below." +
                    "\n (1) Strait " +
                    "\n (2) 4-way intersection " +
                    "\n (3) 2-way intersection ");

            while (!userInput.hasNextInt()) {
                System.out.println("Please select from a number from the list below." +
                        "\n (1) Strait " +
                        "\n (2) 4-way intersection " +
                        "\n (3) 2-way intersection ");
                userInput.next();
            }
            userInputRoad = userInput.nextInt();
        } while (userInputRoad <= 0);
        return userInputRoad;
    }

}

