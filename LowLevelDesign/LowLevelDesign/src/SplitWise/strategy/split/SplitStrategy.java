package SplitWise.strategy.split;

import java.math.BigDecimal;
import java.util.Map;

public interface SplitStrategy {
    Map<String, BigDecimal> calculate(
            BigDecimal total,
            Map<String, BigDecimal> inputs);
}
