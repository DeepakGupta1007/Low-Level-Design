package CollectionPractice;

import java.util.*;

public class HashMapPractice {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();

        List<Integer> ls = new ArrayList<>(List.of(1, 1, 2, 3, 4, 6, 7, 8, 9, 8, 5, 1, -20));

        for (Integer element : ls) {
            map.put(element, map.getOrDefault(element, 0) + 1);
        }

        System.out.println("Demoing HashMap By Doing Intger Frequency");

        for (Map.Entry<Integer, Integer> element : map.entrySet()) {
            System.out.println("Key:" + element.getKey() + " Value:" + element.getValue());
        }

        Map<Integer, Integer> tmp = new TreeMap<>();

        for (Integer element : ls) {
            tmp.put(element, tmp.getOrDefault(element, 0) + 1);
        }
        System.out.println("Demoing TreeMap By Doing Intger Frequency");

        for (Map.Entry<Integer, Integer> element : tmp.entrySet()) {
            System.out.println("Key:" + element.getKey() + " Value:" + element.getValue());
        }

        Map<Integer, Integer> lmp = new LinkedHashMap<>();

        for (int i = ls.size() - 1; i >= 0; i--) {
            lmp.put(ls.get(i), lmp.getOrDefault(ls.get(i), 0) + 1);
        }

        System.out.println("Demoing LinkedHashMap By Doing Intger Frequency (Reverse Order)");

        for (Map.Entry<Integer, Integer> element : lmp.entrySet()) {
            System.out.println("Key:" + element.getKey() + " Value:" + element.getValue());
        }

    }
}
