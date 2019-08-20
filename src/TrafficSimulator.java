import java.util.ArrayList;
import java.util.Scanner;

public class TrafficSimulator {
    public static void main(String[] args) {
        boolean run = true;
        boolean loadMap = true;

        int mapSize = 10;
        int currentSize = 0;
        int x = 2;
        int y = 3;

        ArrayList<Integer> map = new ArrayList<Integer>();
        map.add(x,y);
        System.out.println(map);
/*
        while (currentSize < mapSize){
            System.out.println(currentSize);
            map.add(x,y);

            while (x < 11){
                x += 1;
                map.add(x,y);
            }
            if (x > 10)
                x=0;
            y += 1;
            currentSize += 1;
        }
*/
        displayIntroduction(run);
    }

    private static void displayIntroduction(boolean run) {
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
                userInput = getUserInputIntro(scanner);
        }  else if (userInput == (4)){
                System.out.println("Run Simulation");
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

}

