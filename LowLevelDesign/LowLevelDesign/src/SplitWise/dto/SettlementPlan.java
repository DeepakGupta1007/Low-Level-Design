package SplitWise.dto;

import java.util.List;

public record SettlementPlan(
        long balanceVersion,
        List<PlannedTransfer> transfers) {

    public SettlementPlan {
        transfers = List.copyOf(transfers);
    }
}