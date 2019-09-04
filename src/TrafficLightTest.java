import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficLightTest {
    @Test
    void testTrafficLight(){
        TrafficLight trafficLight = new TrafficLight(3,0,'b',0);
        trafficLight.changeColour();
        assertEquals(trafficLight.getColour(), 1);
        trafficLight.changeColour();
        assertEquals(trafficLight.getColour(), 0);
        trafficLight.colourTimer();
        assertEquals(trafficLight.getChangedColourTimer(),1);
        assertEquals(trafficLight.getColour(),0);
        trafficLight.colourTimer();
        trafficLight.colourTimer();
        trafficLight.colourTimer();
        assertEquals(trafficLight.getChangedColourTimer(),4);
        trafficLight.colourTimer();
        assertEquals(trafficLight.getChangedColourTimer(),0);

    }

}