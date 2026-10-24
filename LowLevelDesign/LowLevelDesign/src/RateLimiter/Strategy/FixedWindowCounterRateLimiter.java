package RateLimiter.Strategy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import RateLimiter.model.UserWindow;

public class FixedWindowCounterRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    public FixedWindowCounterRateLimiter(int maxRequests, int windowInMinutes) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowInMinutes * 60 * 1000L;
    }

    private final ConcurrentMap<String, UserWindow> userWindows = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String userId) {
        if(userId == null || userId.isBlank()){
            throw new IllegalArgumentException("User id cannot be null or empty");
        }

        UserWindow window = userWindows.computeIfAbsent(userId, id -> new UserWindow(System.currentTimeMillis()));

        synchronized(window){
            long now = System.currentTimeMillis();
            long elapsed = now - window.getStartTimeMillis();

            if(elapsed >= windowSizeMillis){
                window.setStartTimeMillis(now);
                window.setRequestCount(0);
            }


            if(window.getRequestCount() < maxRequests){
                window.setRequestCount(window.getRequestCount() + 1);
                return true;
            }

        }
        return false;
    }

}