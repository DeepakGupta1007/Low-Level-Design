package RateLimiter.Strategy;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

class TokenBucketRateLimiter implements RateLimiter {

    private final int capacity;
    private final double tokensPerMillis;

    private final ConcurrentMap<String, UserBucket> userBuckets = new ConcurrentHashMap<>();

    public TokenBucketRateLimiter(
            int capacity,
            int refillTokens,
            int refillMinutes) {
        if (capacity <= 0 || refillTokens <= 0 || refillMinutes <= 0) {
            throw new IllegalArgumentException(
                    "Capacity and refill configuration must be positive");
        }

        this.capacity = capacity;

        long refillPeriodMillis = Duration.ofMinutes(refillMinutes).toMillis();

        this.tokensPerMillis = (double) refillTokens / refillPeriodMillis;
    }

    @Override
    public boolean allowRequest(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID is required");
        }

        UserBucket bucket = userBuckets.computeIfAbsent(
                userId,
                id -> new UserBucket(capacity, nowMillis()));

        synchronized (bucket) {
            long now = nowMillis();
            long elapsedMillis = now - bucket.lastRefillTimeMillis;

            // Refill lazily when a request arrives.
            double newTokens = elapsedMillis * tokensPerMillis;

            bucket.availableTokens = Math.min(
                    capacity,
                    bucket.availableTokens + newTokens);

            bucket.lastRefillTimeMillis = now;

            if (bucket.availableTokens < 1.0) {
                return false;
            }

            bucket.availableTokens -= 1.0;
            return true;
        }
    }

    private static long nowMillis() {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }

    private static class UserBucket {
        private double availableTokens;
        private long lastRefillTimeMillis;

        UserBucket(int capacity, long now) {
            this.availableTokens = capacity;
            this.lastRefillTimeMillis = now;
        }
    }
}