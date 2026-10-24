package RateLimiter.Strategy;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SlidingWindowCounterRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    private final ConcurrentMap<String, UserWindow> userWindows = new ConcurrentHashMap<>();

    public SlidingWindowCounterRateLimiter(
            int maxRequests,
            int windowMinutes) {
        if (maxRequests <= 0 || windowMinutes <= 0) {
            throw new IllegalArgumentException(
                    "Request limit and window minutes must be positive");
        }

        this.maxRequests = maxRequests;
        this.windowSizeMillis = Duration.ofMinutes(windowMinutes).toMillis();
    }

    @Override
    public boolean allowRequest(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID is required");
        }

        UserWindow window = userWindows.computeIfAbsent(
                userId,
                id -> new UserWindow(nowMillis()));

        synchronized (window) {
            long now = nowMillis();
            long elapsed = now - window.startTimeMillis;

            // Move counters forward when the current window expires.
            if (elapsed >= windowSizeMillis) {
                long passedWindows = elapsed / windowSizeMillis;

                if (passedWindows == 1) {
                    window.previousCount = window.currentCount;
                } else {
                    // Both stored windows are now too old.
                    window.previousCount = 0;
                }

                window.currentCount = 0;

                window.startTimeMillis += passedWindows * windowSizeMillis;

                elapsed = now - window.startTimeMillis;
            }

            double previousWindowWeight = 1.0 - (double) elapsed / windowSizeMillis;

            double estimatedCount = window.currentCount
                    + window.previousCount * previousWindowWeight;

            // Include the incoming request before accepting it.
            if (estimatedCount + 1 > maxRequests) {
                return false;
            }

            window.currentCount++;
            return true;
        }
    }

    // Monotonic elapsed time, expressed in readable milliseconds.
    private static long nowMillis() {
        return java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(
                System.nanoTime());
    }

    private static class UserWindow {
        private long startTimeMillis;
        private int previousCount;
        private int currentCount;

        UserWindow(long startTimeMillis) {
            this.startTimeMillis = startTimeMillis;
        }
    }
}