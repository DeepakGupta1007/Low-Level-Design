package ParkingLot;

import ParkingLot.ParkingFloor.ParkingFloor;
import ParkingLot.ParkingSpot.ParkingSpot;
import ParkingLot.ParkingSpot.ParkingSpotType;
import ParkingLot.Vehicle.Vehicle;
import ParkingLot.Vehicle.VehicleType;

import java.util.List;

public class ParkingService {
    public boolean parkVehicle(Vehicle vehicle, List<ParkingFloor> parkingFloors) {
        for(ParkingFloor floor : parkingFloors){
            for(ParkingSpot spot : floor.getParkingSpots()){
                if(checkCanVehicleFit(spot.getType(),vehicle.getType()) && spot.canParkVehicle() ){
                    if(spot.parkVehicle(vehicle)){
                        System.out.println("Vehicle Parked");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkCanVehicleFit(ParkingSpotType parkingSpotType, VehicleType vehicleType) {
        if(parkingSpotType == ParkingSpotType.SMALL){
            if(vehicleType == VehicleType.BIKE){
                return true;
            }
        }

        if(parkingSpotType == ParkingSpotType.MEDIUM){
            if(vehicleType == VehicleType.BIKE || vehicleType == VehicleType.CAR){
                return true;
            }
        }

        if(parkingSpotType == ParkingSpotType.LARGE){
            return vehicleType == VehicleType.BIKE || vehicleType == VehicleType.CAR || vehicleType == VehicleType.BUS;
        }

        return false;

    }

    public boolean unParkVehicle(Vehicle vehicle, List<ParkingFloor> parkingFloors) {
        for(ParkingFloor floor : parkingFloors){
            for(ParkingSpot spot : floor.getParkingSpots()){
                if(spot.getVehicle().equals(vehicle)){
                    spot.freeVehicle();
                    return true;
                }
            }
        }
        return false;
    }
}
