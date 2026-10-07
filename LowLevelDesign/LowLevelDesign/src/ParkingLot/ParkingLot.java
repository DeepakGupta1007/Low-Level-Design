package ParkingLot;

import ParkingLot.ParkingFloor.ParkingFloor;
import ParkingLot.ParkingSpot.LargeParkingSpot;
import ParkingLot.ParkingSpot.MediumParkingSpot;
import ParkingLot.ParkingSpot.SmallParkingSpot;
import ParkingLot.Vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    List<ParkingFloor> parkingfloors ;
    ParkingService parkingService;

    public ParkingLot(){
        parkingService = new ParkingService();
        parkingfloors = new ArrayList<>();
        parkingfloors.add(new ParkingFloor(1,
                List.of(new MediumParkingSpot(11,1),
                        new MediumParkingSpot(12,1),
                        new SmallParkingSpot(13,1),
                        new SmallParkingSpot(14,1),
                        new LargeParkingSpot(15,1))));
        parkingfloors.add(new ParkingFloor(2,
                List.of(new MediumParkingSpot(21, 2),
                        new LargeParkingSpot(22,2),
                        new SmallParkingSpot(23,2),
                        new SmallParkingSpot(24,2)
                        )));
    }


    public boolean parkVehicle(Vehicle vehicle){
        return parkingService.parkVehicle(vehicle,parkingfloors);
    }

    public boolean unparkVehicle(Vehicle vehicle){
        return parkingService.unParkVehicle(vehicle,parkingfloors);
    }

}
