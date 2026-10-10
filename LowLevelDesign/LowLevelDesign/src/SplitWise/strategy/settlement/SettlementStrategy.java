package SplitWise.strategy.settlement;

import SplitWise.dto.PlannedTransfer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SettlementStrategy {
    List<PlannedTransfer> generate(
            Map<String, BigDecimal> netBalances);
}