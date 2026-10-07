package ParkingLot.ParkingFloor;

import ParkingLot.ParkingSpot.ParkingSpot;
import ParkingLot.Vehicle.Vehicle;

import java.util.List;

public class ParkingFloor {
    List<ParkingSpot> parkingSpots ;
    int floor;

    public ParkingFloor(int floor, List<ParkingSpot> spots){
        this.floor = floor;
        this.parkingSpots=spots;
    }

    public List<ParkingSpot> getParkingSpots(){
        return parkingSpots;
    }
}
