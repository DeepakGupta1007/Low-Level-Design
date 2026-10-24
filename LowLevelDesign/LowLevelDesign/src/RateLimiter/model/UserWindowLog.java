package RateLimiter.model;

import java.util.concurrent.ConcurrentLinkedQueue;

public class UserWindowLog {

    private final ConcurrentLinkedQueue<Long> timestamps = new ConcurrentLinkedQueue<>();

    public boolean isEmpty() {
        return timestamps.isEmpty();
    }

    public Long peek() {
        return timestamps.peek();
    }

    public int size() {
        return timestamps.size();
    }

    public void add(Long timestamp) {
        timestamps.add(timestamp);
    }

    public void poll() {
        timestamps.poll();
    }

}
