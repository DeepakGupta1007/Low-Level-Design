package SplitWise.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Settlement {
    public enum Status {
        COMPLETED, REVERSED
    }

    private final String id;
    private final String fromUserId;
    private final String toUserId;
    private final BigDecimal amount;
    private final String idempotencyKey;
    private final String reversalOfId;
    private final Instant createdAt = Instant.now();

    private Status status = Status.COMPLETED;

    public Settlement(String id, String fromUserId, String toUserId,
            BigDecimal amount, String idempotencyKey,
            String reversalOfId) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.idempotencyKey = idempotencyKey;
        this.reversalOfId = reversalOfId;
    }

    public String getId() {
        return id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public String getReversalOfId() {
        return reversalOfId;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void reverse() {
        if (status != Status.COMPLETED) {
            throw new IllegalStateException("Settlement is not active");
        }
        status = Status.REVERSED;
    }
}
