package SplitWise.model;

import java.time.Instant;

public record AuditEvent(
        String action,
        String entityId,
        String details,
        Instant timestamp) {
}
