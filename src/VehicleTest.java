import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VehicleTest {
    @Test
    void changeSpeed(){
        Vehicle vehicle = new Vehicle(67, 0, 3, 0, 0, 0, 1, 'u', "Car", 0);
        vehicle.setSpeed(4);
        vehicle.accelerateVehicle();
        vehicle.accelerateVehicle();
        assertEquals(vehicle.getSpeed(), 5);
        vehicle.decelerateVehicle();
        vehicle.decelerateVehicle();
        vehicle.decelerateVehicle();
        assertEquals(vehicle.getSpeed(), 2);
        vehicle.stopVehicle();
        assertEquals(vehicle.getSpeed(), 0);
    }
    @Test
    void moveVehicle(){
        Vehicle vehicle = new Vehicle(67, 0, 3, 0, 0, 0, 1, 'u', "Car", 0);
        vehicle.setSpeed(2);
        vehicle.moveVehicleRoadLocation();
        assertEquals(vehicle.getRoadLocation(), 2);
    }
}