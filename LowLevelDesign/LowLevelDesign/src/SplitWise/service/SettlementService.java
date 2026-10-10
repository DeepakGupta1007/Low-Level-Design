package SplitWise.service;



import SplitWise.dto.*;
import SplitWise.model.*;
import SplitWise.strategy.settlement.*;
import SplitWise.util.Money;
import java.math.BigDecimal;
import java.util.*;

public class SettlementService {
    private final BalanceService balanceService;
    private final SettlementStrategyFactory factory;

    public SettlementService(
            BalanceService balanceService,
            SettlementStrategyFactory factory) {
        this.balanceService = balanceService;
        this.factory = factory;
    }

    // Preview does not mutate expenses, settlements, or balances.
    public SettlementPlan preview(
            Group group, SettlementStrategyFactory.Type type) {

        group.getLock().lock();
        try {
            var balances = balanceService.getNetBalances(group);
            var transfers = factory.get(type).generate(balances);

            return new SettlementPlan(
                    group.getBalanceVersion(), transfers);
        } finally {
            group.getLock().unlock();
        }
    }

    public Settlement confirm(
            Group group,
            SettlementPlan plan,
            PlannedTransfer transfer,
            String idempotencyKey) {

        group.getLock().lock();
        try {
            String existingId =
                    group.getSettlementIdempotency().get(idempotencyKey);

            if (existingId != null) {
                return group.getSettlements().get(existingId);
            }

            if (plan.balanceVersion() != group.getBalanceVersion()) {
                throw new IllegalStateException(
                        "Balances changed; generate a new preview");
            }

            if (!plan.transfers().contains(transfer)) {
                throw new IllegalArgumentException(
                        "Transfer is not in the preview");
            }

            group.requireMember(transfer.fromUserId());
            group.requireMember(transfer.toUserId());

            var balances = balanceService.getNetBalances(group);
            BigDecimal debtor = balances.get(transfer.fromUserId());
            BigDecimal creditor = balances.get(transfer.toUserId());

            if (debtor == null || creditor == null
                    || debtor.signum() >= 0 || creditor.signum() <= 0
                    || transfer.amount().compareTo(debtor.abs()) > 0
                    || transfer.amount().compareTo(creditor) > 0) {
                throw new IllegalArgumentException(
                        "Transfer exceeds current balances");
            }

            Settlement settlement = new Settlement(
                    UUID.randomUUID().toString(),
                    transfer.fromUserId(),
                    transfer.toUserId(),
                    Money.normalize(transfer.amount()),
                    idempotencyKey,
                    null);

            group.getSettlements().put(settlement.getId(), settlement);
            group.getSettlementIdempotency().put(
                    idempotencyKey, settlement.getId());

            group.incrementBalanceVersion();
            group.audit("SETTLEMENT_COMPLETED", settlement.getId(),
                    transfer.fromUserId() + " pays "
                            + transfer.toUserId());

            return settlement;
        } finally {
            group.getLock().unlock();
        }
    }

    public void reverse(Group group, String settlementId) {
        group.getLock().lock();
        try {
            Settlement settlement =
                    group.getSettlements().get(settlementId);

            if (settlement == null) {
                throw new IllegalArgumentException("Settlement not found");
            }

            settlement.reverse();
            group.incrementBalanceVersion();
            group.audit("SETTLEMENT_REVERSED", settlementId,
                    "Original settlement retained");
        } finally {
            group.getLock().unlock();
        }
    }
}
