package SplitWise.dto;

import java.math.BigDecimal;

public record PlannedTransfer(
        String fromUserId,
        String toUserId,
        BigDecimal amount) {
}
