import Elevtaor.ElevatorController;
import Elevtaor.RequestType;
import ParkingLot.ParkingLot;
import ParkingLot.Vehicle.Bus;
import ParkingLot.Vehicle.Vehicle;
import SplitWise.dto.SettlementPlan;
import SplitWise.model.Group;
import SplitWise.model.User;
import SplitWise.service.BalanceService;
import SplitWise.service.ExpenseService;
import SplitWise.service.SettlementService;
import SplitWise.strategy.settlement.SettlementStrategyFactory;
import SplitWise.strategy.split.SplitStrategyFactory;
import SplitWise.util.Money;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Deepak Gupta LLD Codes");
        System.out.println("Enter number for simulating Design Code \n 1. Parking Lot \n 2. Elevator Controller");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        switch (number) {
            case 1:
                ParkingLot lot = new ParkingLot();
                Vehicle bus1 = new Bus("Ashok Leyland", "9080");
                Vehicle bus2 = new Bus("Tata Motors", "9090");

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
                Group group = new Group("G1", "Weekend Trip");

                group.addMember(new User("A", "Asha"));
                group.addMember(new User("B", "Bharat"));
                group.addMember(new User("C", "Charu"));

                BalanceService balanceService = new BalanceService();

                ExpenseService expenseService = new ExpenseService(new SplitStrategyFactory());

                SettlementService settlementService = new SettlementService(
                        balanceService,
                        new SettlementStrategyFactory());

                expenseService.addExpense(
                        group, "expense-1", "A",
                        Money.of("900.00"), "Dinner",
                        SplitStrategyFactory.Type.EQUAL,
                        Map.of(
                                "A", Money.zero(),
                                "B", Money.zero(),
                                "C", Money.zero()));

                expenseService.addExpense(
                        group, "expense-2", "B",
                        Money.of("600.00"), "Cab",
                        SplitStrategyFactory.Type.EQUAL,
                        Map.of(
                                "B", Money.zero(),
                                "C", Money.zero()));

                System.out.println("Balances:");
                balanceService.getNetBalances(group)
                        .forEach((id, balance) -> System.out.println(id + ": " + balance));

                SettlementPlan plan = settlementService.preview(
                        group, SettlementStrategyFactory.Type.GREEDY);

                System.out.println("\nSettlement preview:");
                plan.transfers().forEach(t -> System.out.println(
                        t.fromUserId() + " pays "
                                + t.toUserId() + ": " + t.amount()));

                if (!plan.transfers().isEmpty()) {
                    settlementService.confirm(
                            group, plan, plan.transfers().get(0),
                            "settlement-1");
                }

                System.out.println("\nAudit events:");
                group.getAuditLog().forEach(System.out::println);

        }

    }
}