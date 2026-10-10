package SplitWise.strategy.split;

import SplitWise.util.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PercentageSplitStrategy implements SplitStrategy {
    @Override
    public Map<String, BigDecimal> calculate(
            BigDecimal total, Map<String, BigDecimal> inputs) {

        BigDecimal percentageTotal = inputs.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (inputs.isEmpty()
                || percentageTotal.compareTo(new BigDecimal("100")) != 0) {
            throw new IllegalArgumentException(
                    "Percentages must sum to 100");
        }

        List<String> ids = new ArrayList<>(inputs.keySet());
        Collections.sort(ids);

        Map<String, BigDecimal> result = new LinkedHashMap<>();
        BigDecimal allocated = Money.zero();

        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i);
            BigDecimal percentage = inputs.get(id);

            if (percentage.signum() < 0) {
                throw new IllegalArgumentException("Negative percentage");
            }

            BigDecimal share;
            if (i == ids.size() - 1) {
                share = total.subtract(allocated);
            } else {
                share = total.multiply(percentage)
                        .divide(new BigDecimal("100"), 2, RoundingMode.DOWN);
                allocated = allocated.add(share);
            }

            result.put(id, Money.normalize(share));
        }

        return result;
    }
}
