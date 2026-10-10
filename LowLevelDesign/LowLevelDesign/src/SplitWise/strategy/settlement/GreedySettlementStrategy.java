package SplitWise.strategy.settlement;

import SplitWise.dto.PlannedTransfer;
import SplitWise.util.Money;
import java.math.BigDecimal;
import java.util.*;

public class GreedySettlementStrategy implements SettlementStrategy {
    @Override
    public List<PlannedTransfer> generate(
            Map<String, BigDecimal> netBalances) {

        Map<String, BigDecimal> remaining =
                new LinkedHashMap<>(netBalances);

        List<String> debtors = new ArrayList<>();
        List<String> creditors = new ArrayList<>();

        for (var entry : remaining.entrySet()) {
            if (entry.getValue().signum() < 0) {
                debtors.add(entry.getKey());
            } else if (entry.getValue().signum() > 0) {
                creditors.add(entry.getKey());
            }
        }

        debtors.sort(Comparator.comparing(
                (String id) -> remaining.get(id).abs()).reversed());

        creditors.sort(Comparator.comparing(
                (String id) -> remaining.get(id)).reversed());

        List<PlannedTransfer> result = new ArrayList<>();
        int d = 0, c = 0;

        while (d < debtors.size() && c < creditors.size()) {
            String debtor = debtors.get(d);
            String creditor = creditors.get(c);

            BigDecimal amount = remaining.get(debtor).abs()
                    .min(remaining.get(creditor));

            result.add(new PlannedTransfer(debtor, creditor, amount));

            remaining.put(debtor, remaining.get(debtor).add(amount));
            remaining.put(creditor,
                    remaining.get(creditor).subtract(amount));

            if (Money.isZero(remaining.get(debtor))) d++;
            if (Money.isZero(remaining.get(creditor))) c++;
        }

        return result;
    }
}
