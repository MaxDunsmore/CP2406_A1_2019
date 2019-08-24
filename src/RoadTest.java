import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    @Test
    void testRoad(){
        Road road = new Road("straight", 1,10);
        assertEquals(road.getLocation(),10.4);
        assertEquals(road.getName(),"straight");
        assertEquals(road.getOrientation(),1);
        road.printRoad();
    }
}