package ParkingLot.Vehicle;

public class VehicleFactory {
    static private VehicleFactory vehicleFactory;
    synchronized public VehicleFactory getVehicleFactory(){
        if(vehicleFactory  == null){
            vehicleFactory = new VehicleFactory();
        }
        return vehicleFactory;
    }

    public Vehicle getVehicle(VehicleType type, String vehicleNumber, String vehicleName){
        switch (type){
            case BIKE ->  new Motorcycle(vehicleName,vehicleNumber);
            case BUS -> new Bus(vehicleName,vehicleNumber);
            case CAR -> new Car(vehicleName,vehicleNumber);
            default -> throw new NoClassDefFoundError("No Vehicle Support this type");
        }
        return null;
    }
}
