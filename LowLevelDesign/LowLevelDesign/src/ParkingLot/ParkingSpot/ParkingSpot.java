package ParkingLot.ParkingSpot;

import ParkingLot.Vehicle.Vehicle;

public abstract class ParkingSpot {
    private int parkingSpotId;
    private int floor;
    private ParkingSpotType type;
    private Vehicle vehicle;

    public ParkingSpot(int parkingSpotId,int floor, ParkingSpotType type){
        this.parkingSpotId=parkingSpotId;
        this.floor=floor;
        this.type=type;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean canParkVehicle(){
        System.out.println("Vehicle can park on " + floor + " parking spot " + parkingSpotId);
        return vehicle == null;
    }

    public synchronized boolean parkVehicle(Vehicle vehicle){
        if(this.vehicle == null){
            System.out.println("Vehicle " + vehicle.toString() + " parked on " + floor + " parking spot " + parkingSpotId);
            this.vehicle = vehicle;
            return true;
        }
        return false;
    }

    public void freeVehicle(){
        System.out.println("Vehicle " + vehicle.toString() + " unparked on " + floor + " parking spot " + parkingSpotId);
        this.vehicle = null;
    }

    public ParkingSpotType getType(){
        return type;
    }
}
