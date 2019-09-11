import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrafficLightTest {
    @Test
    void testTrafficLight(){
        TrafficLight trafficLight = new TrafficLight(3,0,'n',0,1,"Straight",0);
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
        trafficLight.setLightCycle2Way();
        assertEquals(trafficLight.getLightCycle(),1);
        trafficLight.setLightCycle2Way();
        trafficLight.setLightCycle2Way();
        assertEquals(trafficLight.getLightCycle(),3);
        trafficLight.setLightCycle2Way();
        assertEquals(trafficLight.getLightCycle(),0);
        trafficLight.setLightCycle2Way();
        assertEquals(trafficLight.getLightCycle(),1);

        TrafficLight trafficLight4Way1 = new TrafficLight(4,0,'n',0,1,"4-way intersection",1);
        trafficLight4Way1.setLightCycle4Way();
        assertEquals(trafficLight4Way1.getLightCycle(),1.5);
        trafficLight4Way1.setLightCycle4Way();
        assertEquals(trafficLight4Way1.getLightCycle(),2);
        trafficLight4Way1.setLightCycle4Way();
        trafficLight4Way1.setLightCycle4Way();
        trafficLight4Way1.setLightCycle4Way();
        trafficLight4Way1.setLightCycle4Way();
        trafficLight4Way1.setLightCycle4Way();
        trafficLight4Way1.setLightCycle4Way();
        trafficLight4Way1.setLightCycle4Way();
        assertEquals(trafficLight4Way1.getLightCycle(),1.5);
    }

}