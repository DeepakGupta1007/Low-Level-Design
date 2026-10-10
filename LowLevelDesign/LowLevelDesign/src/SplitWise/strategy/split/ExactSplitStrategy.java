package SplitWise.strategy.split;

import SplitWise.util.Money;
import java.math.BigDecimal;
import java.util.*;

public class ExactSplitStrategy implements SplitStrategy {
    @Override
    public Map<String, BigDecimal> calculate(
            BigDecimal total, Map<String, BigDecimal> inputs) {

        Map<String, BigDecimal> result = new LinkedHashMap<>();
        BigDecimal sum = Money.zero();

        for (var entry : inputs.entrySet()) {
            BigDecimal share = Money.normalize(entry.getValue());

            if (share.signum() < 0) {
                throw new IllegalArgumentException("Negative share");
            }

            result.put(entry.getKey(), share);
            sum = sum.add(share);
        }

        if (sum.compareTo(total) != 0) {
            throw new IllegalArgumentException("Shares must equal total");
        }

        return result;
    }
}
