package LFUCache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache {

    Map<Integer, Integer> keyToValueMap = new HashMap<>();
    Map<Integer, Integer> keyToFrequencyMap = new HashMap<>();
    Map<Integer, LinkedHashSet<Integer>> frequencyToKeysMap = new HashMap<>();

    int minFreq=0;

    int cacheCapacity;
    
    public LFUCache(int capacity) {
        this.cacheCapacity = capacity;
    }

    public int get(int key){

        if(!keyToValueMap.containsKey(key)){
            return -1;
        }

        updateFrequency(key);
        return keyToValueMap.get(key);
    }

    public void put(int key, int value){
        if(cacheCapacity == 0){
            return;
        }

        if(keyToValueMap.containsKey(key)){
            keyToValueMap.put(key, value);
            updateFrequency(key);
            return;
        }

        if(keyToValueMap.size() == cacheCapacity){
            //Remove the LFU element
            LinkedHashSet<Integer> lfuKeys = frequencyToKeysMap.get(minFreq);
            int lfuKey = lfuKeys.iterator().next();
            lfuKeys.remove(lfuKey);
            keyToValueMap.remove(lfuKey);
            keyToFrequencyMap.remove(lfuKey);

            if(lfuKeys.isEmpty()){
                frequencyToKeysMap.remove(minFreq);
            }
        }

        keyToValueMap.put(key, value);
        keyToFrequencyMap.put(key, 1);
        minFreq=1;
        
        frequencyToKeysMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);


    }

    private void updateFrequency(int key) {
        int freq = keyToFrequencyMap.get(key);

        LinkedHashSet<Integer> keys = frequencyToKeysMap.get(freq);
        keys.remove(key);

        if(keys.isEmpty()){
            frequencyToKeysMap.remove(key);
            if(freq == minFreq){
                minFreq++;
            }
        }

        int newFreq = freq+1;
        keyToFrequencyMap.put(key, newFreq);

        frequencyToKeysMap.computeIfAbsent(newFreq, k -> new LinkedHashSet<>())
                  .add(key);

    }
        
    
}
