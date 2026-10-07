package CollectionPractice;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapPractice {
    public static void main(String[] args) {
        Map<Integer, Integer> mp = new HashMap<>();

        mp.put(1, 0);

        Runnable task = new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    mp.put(1, mp.get(1) + 1);
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Final value of 1 is:" + mp.get(1));

        ConcurrentHashMap<Integer, Integer> c = new ConcurrentHashMap<>();
        c.put(1, 0);

        Runnable task2 = new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    c.merge(1, 1, Integer::sum);
                }
            }
        };

        Thread t3 = new Thread(task2);
        Thread t4 = new Thread(task2);

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Final value of 1 is:" + c.get(1));

    }
}
