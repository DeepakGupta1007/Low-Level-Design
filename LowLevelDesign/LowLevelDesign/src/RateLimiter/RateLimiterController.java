package RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import RateLimiter.Strategy.FixedWindowCounterRateLimiter;
import RateLimiter.Strategy.RateLimiter;
import RateLimiter.Strategy.SlidingWindowCounterRateLimiter;

public class RateLimiterController {

    static RateLimiterController rateLimiterController;

    private RateLimiterController() {

    }

    public static RateLimiterController getInstance() {
        if (rateLimiterController == null) {
            rateLimiterController = new RateLimiterController();
        }
        return rateLimiterController;
    }

    public void demonstrateRateLimiterFixedWindowCounter() {
        try {
            RateLimiter limiter = new FixedWindowCounterRateLimiter(5, 1);

            AtomicInteger allowedCount = new AtomicInteger();
            List<Thread> threads = new ArrayList<>();

            // Same task executed by 20 threads.
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    boolean allowed = limiter.allowRequest("alice");

                    if (allowed) {
                        allowedCount.incrementAndGet();
                    }

                    System.out.println(
                            Thread.currentThread().getName() + " -> "
                                    + (allowed ? "ALLOWED" : "REJECTED"));
                }
            };

            for (int i = 1; i <= 20; i++) {
                Thread thread = new Thread(task, "Request-" + i);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("\nAllowed: " + allowedCount.get());
            System.out.println("Rejected: " + (20 - allowedCount.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void demonstrateRateLimiterSlidingWindowCounter() {
        try {
            RateLimiter limiter = new SlidingWindowCounterRateLimiter(5, 1);

            AtomicInteger allowedCount = new AtomicInteger();
            List<Thread> threads = new ArrayList<>();

            // Same task executed by 20 threads.
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    boolean allowed = limiter.allowRequest("alice");

                    if (allowed) {
                        allowedCount.incrementAndGet();
                    }

                    System.out.println(
                            Thread.currentThread().getName() + " -> "
                                    + (allowed ? "ALLOWED" : "REJECTED"));
                }
            };

            for (int i = 1; i <= 20; i++) {
                Thread thread = new Thread(task, "Request-" + i);
                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("\nAllowed: " + allowedCount.get());
            System.out.println("Rejected: " + (20 - allowedCount.get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
