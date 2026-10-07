package ParkingLot.Vehicle;

public abstract class Vehicle {
    private String vehicleNumber;
    private String vehicleName;
    private VehicleType type;

    public Vehicle(String vehicleName,String vehicleNumber, VehicleType type){
        this.vehicleName = vehicleName;
        this.vehicleNumber = vehicleNumber;
        this.type = type;
    }

    public String getVehicleNumber(){
        return vehicleNumber;
    }

    public String getVehicleName(){
        return vehicleName;
    }

    public VehicleType getType(){
        return type;
    }

    @Override
    public String toString(){
        return vehicleName+vehicleNumber;
    }
}
