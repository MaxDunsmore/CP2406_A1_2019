import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficLightTest {
    @Test
    void testTrafficLightColorTimer(){
        TrafficLight trafficLight = new TrafficLight(3,0,'n',0,1,"Straight",0);
        trafficLight.colourTimer();
        trafficLight.colourTimer();
        trafficLight.colourTimer();
        assertEquals(trafficLight.getChangedColourTimer(),3);
        trafficLight.colourTimer();
        trafficLight.colourTimer();
        trafficLight.colourTimer();
        assertEquals(trafficLight.getChangedColourTimer(),1);
    }
    @Test
    void testTrafficLightCycle2Way(){
        TrafficLight trafficLight = new TrafficLight(3,1,'n',0,2,"threeWayOne",0);
        for (int i2 = 0; i2 < 22; i2++){
            trafficLight.threeWayCycle();
        }
        assertEquals(trafficLight.getColour(),0);
        for (int i2 = 0; i2 < 42; i2++){
            trafficLight.threeWayCycle();
        }
        assertEquals(trafficLight.getColour(),1);
    }
    @Test
    void testTrafficLightCycle4Way(){
        TrafficLight trafficLight = new TrafficLight(3,1,'n',0,2,"fourWay",0);
        for (int i2 = 0; i2 < 42; i2++){
            trafficLight.fourWayCycle();
        }
        assertEquals(trafficLight.getColour(),0);
        for (int i2 = 0; i2 < 61; i2++){
            trafficLight.fourWayCycle();
        }
        assertEquals(trafficLight.getColour(),1);
    }
    @Test
    void testTrafficLightChangeColour(){
        TrafficLight trafficLight = new TrafficLight(3,0,'n',0,1,"Straight",0);
        trafficLight.changeColour();
        assertEquals(trafficLight.getColour(), 1);
        trafficLight.changeColour();
        assertEquals(trafficLight.getColour(), 0);
    }
}