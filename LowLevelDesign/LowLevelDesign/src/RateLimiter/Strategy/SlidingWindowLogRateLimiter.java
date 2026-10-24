package RateLimiter.Strategy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import RateLimiter.model.UserWindowLog;

public class SlidingWindowLogRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    public SlidingWindowLogRateLimiter(int maxRequests, int windowInMinutes){
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowInMinutes * 60 * 1000L;
    }

    private final ConcurrentMap<String, UserWindowLog> userWindows = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String userId) {
        Long now = System.currentTimeMillis();

        UserWindowLog userWindowLog = userWindows.computeIfAbsent(userId, id -> new UserWindowLog());

        synchronized (userWindowLog) {
            long windowStart = now - windowSizeMillis;

            // 1. Remove old timestamps
            while (!userWindowLog.isEmpty() && userWindowLog.peek() <= windowStart) {
                userWindowLog.poll();
            }

            // 2. Check if we are within limit
            if (userWindowLog.size() < maxRequests) {
                userWindowLog.add(now);
                return true;
            }

        }

        return false;

    }

}
