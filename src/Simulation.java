import java.util.ArrayList;
import java.util.TimerTask;

public class Simulation extends TimerTask {

    private final int cars;
    private final int[] currentMap;
    private final ArrayList<Road> roadArrayList;
    private final int map;


    Simulation(int cars, int[] currentMap, ArrayList<Road> roadArrayList, int map) {
        this.cars = cars;
        this.currentMap = currentMap;
        this.roadArrayList = roadArrayList;
        this.map = map;
    }

    @Override
    public void run() {
        Vehicle vehicle = new Vehicle(1, 8, 5, 1);

    }
}
