package SplitWise.strategy.split;

import java.math.BigDecimal;
import java.util.*;

public class EqualSplitStrategy implements SplitStrategy {
    @Override
    public Map<String, BigDecimal> calculate(
            BigDecimal total, Map<String, BigDecimal> inputs) {

        if (inputs.isEmpty()) {
            throw new IllegalArgumentException("No participants");
        }

        List<String> ids = new ArrayList<>(inputs.keySet());
        Collections.sort(ids);

        long cents = total.movePointRight(2).longValueExact();
        long base = cents / ids.size();
        long remainder = cents % ids.size();

        Map<String, BigDecimal> result = new LinkedHashMap<>();

        for (int i = 0; i < ids.size(); i++) {
            long share = base + (i < remainder ? 1 : 0);
            result.put(ids.get(i), BigDecimal.valueOf(share, 2));
        }

        return result;
    }
}
