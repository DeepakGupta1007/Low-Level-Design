package SplitWise.service;

import SplitWise.model.*;
import SplitWise.strategy.split.*;
import SplitWise.util.Money;
import java.math.BigDecimal;
import java.util.*;

public class ExpenseService {
    private final SplitStrategyFactory factory;

    public ExpenseService(SplitStrategyFactory factory) {
        this.factory = factory;
    }

    public Expense addExpense(
            Group group,
            String idempotencyKey,
            String payerId,
            BigDecimal amount,
            String description,
            SplitStrategyFactory.Type splitType,
            Map<String, BigDecimal> inputs) {

        amount = Money.normalize(amount);

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        group.getLock().lock();
        try {
            String existingId =
                    group.getExpenseIdempotency().get(idempotencyKey);

            if (existingId != null) {
                return group.getExpenses().get(existingId);
            }

            group.requireMember(payerId);
            inputs.keySet().forEach(group::requireMember);

            Map<String, BigDecimal> shares =
                    factory.get(splitType).calculate(amount, inputs);

            BigDecimal sum = shares.values().stream()
                    .reduce(Money.zero(), BigDecimal::add);

            if (sum.compareTo(amount) != 0) {
                throw new IllegalArgumentException("Invalid share total");
            }

            Expense expense = new Expense(
                    UUID.randomUUID().toString(),
                    payerId, amount, shares, description);

            group.getExpenses().put(expense.getId(), expense);
            group.getExpenseIdempotency().put(
                    idempotencyKey, expense.getId());

            group.incrementBalanceVersion();
            group.audit("EXPENSE_CREATED", expense.getId(), description);

            return expense;
        } finally {
            group.getLock().unlock();
        }
    }

    public void reverseExpense(Group group, String expenseId) {
        group.getLock().lock();
        try {
            Expense expense = group.getExpenses().get(expenseId);

            if (expense == null) {
                throw new IllegalArgumentException("Expense not found");
            }

            expense.reverse();
            group.incrementBalanceVersion();
            group.audit("EXPENSE_REVERSED", expenseId, "Reversed");
        } finally {
            group.getLock().unlock();
        }
    }
}
