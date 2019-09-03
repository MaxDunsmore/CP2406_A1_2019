import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {
    @Test
    void testVehicle(){
        Vehicle vehicle = new Vehicle();
        vehicle.accelerateVehicle();
        vehicle.accelerateVehicle();
        vehicle.accelerateVehicle();
        vehicle.accelerateVehicle();
        vehicle.accelerateVehicle();
        vehicle.accelerateVehicle();
        assertEquals(vehicle.getSpeed(), 5);
        vehicle.moveVehicleRoadLocation();
        assertEquals(vehicle.getRoadLocation(), 5);
        vehicle.decelerateVehicle();
        vehicle.decelerateVehicle();
        vehicle.decelerateVehicle();
        assertEquals(vehicle.getSpeed(), 2);
        vehicle.moveVehicleRoadLocation();
        assertEquals(vehicle.getRoadLocation(), 7);
        vehicle.stopVehicle();
        assertEquals(vehicle.getSpeed(), 0);
    }
}