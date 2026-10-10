package SplitWise.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class Expense {
    public enum Status {
        ACTIVE, REVERSED
    }

    private final String id;
    private final String payerId;
    private final BigDecimal amount;
    private final Map<String, BigDecimal> shares;
    private final String description;
    private final Instant createdAt = Instant.now();

    private Status status = Status.ACTIVE;

    public Expense(String id, String payerId, BigDecimal amount,
            Map<String, BigDecimal> shares, String description) {
        this.id = id;
        this.payerId = payerId;
        this.amount = amount;
        this.shares = Map.copyOf(shares);
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getPayerId() {
        return payerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Map<String, BigDecimal> getShares() {
        return shares;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void reverse() {
        if (status == Status.REVERSED) {
            throw new IllegalStateException("Expense already reversed");
        }
        status = Status.REVERSED;
    }
}
