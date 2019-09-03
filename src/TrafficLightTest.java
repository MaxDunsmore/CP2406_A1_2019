import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficLightTest {
    @Test
    void testTrafficLight(){
        TrafficLight trafficLight = new TrafficLight(3,0,'b');
        trafficLight.changeColour();
        assertEquals(trafficLight.getColour(), 1);
        trafficLight.changeColour();
        assertEquals(trafficLight.getColour(), 0);
    }

}