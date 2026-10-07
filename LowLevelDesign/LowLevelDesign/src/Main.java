import Elevtaor.ElevatorController;
import Elevtaor.RequestType;
import ParkingLot.ParkingLot;
import ParkingLot.Vehicle.Bus;
import ParkingLot.Vehicle.Vehicle;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Deepak Gupta LLD Codes");
        System.out.println("Enter number for simulating Design Code \n 1. Parking Lot \n 2. Elevator Controller");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        switch(number){
            case 1:
                ParkingLot lot = new ParkingLot();
                Vehicle bus1 = new Bus("Ashok Leyland","9080");
                Vehicle bus2= new Bus("Tata Motors","9090");

                List<Vehicle> buses = List.of(bus1, bus2);

                buses.parallelStream().forEach(lot::parkVehicle);
                break;
            case 2:
                ElevatorController controller = new ElevatorController();

                // Person on floor 3 wants to go UP
                controller.requestElevator(3, RequestType.PICKUP_UP);

                // Person on floor 7 wants to go DOWN
                controller.requestElevator(7, RequestType.PICKUP_DOWN);

                // Simulate time
                for (int i = 0; i < 15; i++) {
                    System.out.println("---- Step " + i + " ----");

                    controller.step();

                    // Print current state
                    controller.printStatus();
                }
                break;

            case 3:
                
        }

    }
}