package SplitWise.model;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Group {
    private final String id;
    private final String name;

    private final Map<String, User> members = new LinkedHashMap<>();
    private final Map<String, Expense> expenses = new LinkedHashMap<>();
    private final Map<String, Settlement> settlements = new LinkedHashMap<>();

    private final Map<String, String> expenseIdempotency = new HashMap<>();
    private final Map<String, String> settlementIdempotency = new HashMap<>();

    private final List<AuditEvent> auditLog = new ArrayList<>();

    // In-memory concurrency control for this group.
    private final ReentrantLock lock = new ReentrantLock();
    private long balanceVersion;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, User> getMembers() {
        return members;
    }

    public Map<String, Expense> getExpenses() {
        return expenses;
    }

    public Map<String, Settlement> getSettlements() {
        return settlements;
    }

    public Map<String, String> getExpenseIdempotency() {
        return expenseIdempotency;
    }

    public Map<String, String> getSettlementIdempotency() {
        return settlementIdempotency;
    }

    public List<AuditEvent> getAuditLog() {
        return auditLog;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public long getBalanceVersion() {
        return balanceVersion;
    }

    public void incrementBalanceVersion() {
        balanceVersion++;
    }

    public void addMember(User user) {
        lock.lock();
        try {
            if (members.size() >= 100) {
                throw new IllegalStateException("Group limit reached");
            }
            if (members.putIfAbsent(user.id(), user) != null) {
                throw new IllegalArgumentException("Duplicate member");
            }
            audit("MEMBER_ADDED", user.id(), user.name());
        } finally {
            lock.unlock();
        }
    }

    public void requireMember(String userId) {
        if (!members.containsKey(userId)) {
            throw new IllegalArgumentException("Not a group member: " + userId);
        }
    }

    public void audit(String action, String entityId, String details) {
        auditLog.add(new AuditEvent(
                action, entityId, details, java.time.Instant.now()));
    }
}
