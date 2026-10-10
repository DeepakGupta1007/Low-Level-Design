
package SplitWise;

import SplitWise.dto.*;
import SplitWise.model.*;
import SplitWise.service.*;
import SplitWise.strategy.split.*;
import SplitWise.strategy.settlement.*;
import SplitWise.util.Money;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
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
