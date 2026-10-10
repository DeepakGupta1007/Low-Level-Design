package SplitWise.strategy.settlement;

import SplitWise.dto.PlannedTransfer;
import SplitWise.util.Money;
import java.math.BigDecimal;
import java.util.*;

public class OptimalSettlementStrategy implements SettlementStrategy {
    private List<String> ids;
    private BigDecimal[] balances;
    private List<PlannedTransfer> current;
    private List<PlannedTransfer> best;

    @Override
    public List<PlannedTransfer> generate(
            Map<String, BigDecimal> netBalances) {

        ids = new ArrayList<>(netBalances.keySet());
        balances = new BigDecimal[ids.size()];

        for (int i = 0; i < ids.size(); i++) {
            balances[i] = netBalances.get(ids.get(i));
        }

        current = new ArrayList<>();
        best = null;
        backtrack(0);

        return best == null ? List.of() : List.copyOf(best);
    }

    private void backtrack(int start) {
        while (start < balances.length
                && Money.isZero(balances[start])) {
            start++;
        }

        if (start == balances.length) {
            if (best == null || current.size() < best.size()) {
                best = new ArrayList<>(current);
            }
            return;
        }

        if (best != null && current.size() >= best.size()) {
            return;
        }

        Set<BigDecimal> tried = new HashSet<>();

        for (int i = start + 1; i < balances.length; i++) {
            if (Money.isZero(balances[i])
                    || balances[start].signum() == balances[i].signum()) {
                continue;
            }

            // Equivalent balances need not be explored repeatedly.
            if (!tried.add(balances[i])) continue;

            BigDecimal oldStart = balances[start];
            BigDecimal oldI = balances[i];
            BigDecimal amount = oldStart.abs().min(oldI.abs());

            String from = oldStart.signum() < 0
                    ? ids.get(start) : ids.get(i);
            String to = oldStart.signum() > 0
                    ? ids.get(start) : ids.get(i);

            balances[start] = oldStart.signum() < 0
                    ? oldStart.add(amount) : oldStart.subtract(amount);
            balances[i] = oldI.signum() < 0
                    ? oldI.add(amount) : oldI.subtract(amount);

            current.add(new PlannedTransfer(from, to, amount));

            backtrack(Money.isZero(balances[start]) ? start + 1 : start);

            current.remove(current.size() - 1);
            balances[start] = oldStart;
            balances[i] = oldI;

            // Exact cancellation is a useful symmetry-pruning rule.
            if (oldStart.abs().compareTo(oldI.abs()) == 0) {
                break;
            }
        }
    }
}
