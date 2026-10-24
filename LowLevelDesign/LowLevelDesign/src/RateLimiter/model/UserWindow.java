package RateLimiter.model;

public class UserWindow {
    private long startTimeMillis;
    private int requestCount;

    public UserWindow(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

}