package SplitWise.service;

import SplitWise.model.*;
import SplitWise.util.Money;
import java.math.BigDecimal;
import java.util.*;

public class BalanceService {
    public Map<String, BigDecimal> getNetBalances(Group group) {
        Map<String, BigDecimal> balances = new LinkedHashMap<>();

        group.getMembers().keySet()
                .forEach(id -> balances.put(id, Money.zero()));

        for (Expense expense : group.getExpenses().values()) {
            if (expense.getStatus() != Expense.Status.ACTIVE) continue;

            balances.merge(expense.getPayerId(),
                    expense.getAmount(), BigDecimal::add);

            expense.getShares().forEach((userId, share) ->
                    balances.merge(userId, share.negate(), BigDecimal::add));
        }

        for (Settlement settlement : group.getSettlements().values()) {
            if (settlement.getStatus() != Settlement.Status.COMPLETED) {
                continue;
            }

            balances.merge(settlement.getFromUserId(),
                    settlement.getAmount(), BigDecimal::add);

            balances.merge(settlement.getToUserId(),
                    settlement.getAmount().negate(), BigDecimal::add);
        }

        balances.replaceAll((id, value) -> Money.normalize(value));

        BigDecimal sum = balances.values().stream()
                .reduce(Money.zero(), BigDecimal::add);

        if (!Money.isZero(sum)) {
            throw new IllegalStateException("Balances do not sum to zero");
        }

        return balances;
    }
}
