package com.jdev.util;

import com.jdev.exceptions.HelperException;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtils {

    public static <K, V> Map<K, V> createAndFillMap(K[] keys, V[] values) {
        if (keys.length == values.length) {
            Map<K, V> kvMap = new HashMap<>();
            for (int i = 0; i < keys.length; i++) {
                kvMap.put(keys[i], values[i]);
            }
            return kvMap;
        } else {
            throw new HelperException("Can't fill Map, because keys length is - " + keys.length + " not equal with values length - " + values.length);
        }
    }

    public static <K, V> Map<K, V> createAndFillMap(PairForMap<K, V>[] kvPairForMap) {
        Map<K, V> kvMap = new HashMap<>();
        for (PairForMap<K, V> kvPairForMapCurrent : kvPairForMap) {
            kvMap.put(kvPairForMapCurrent.getKey(), kvPairForMapCurrent.getValue());
        }
        return kvMap;
    }

    public static <K, V> Map<K, V> createAndFillMap(List<PairForMap<K, V>> kvPairForMap) {
        Map<K, V> kvMap = new HashMap<>();
        for (PairForMap<K, V> kvPairForMapCurrent : kvPairForMap) {
            kvMap.put(kvPairForMapCurrent.getKey(), kvPairForMapCurrent.getValue());
        }
        return kvMap;
    }

    @Data
    public static class PairForMap<K, V> {
        private final K key;
        private final V value;

        public static <K, V> PairForMap<K, V> createKvPairForMap(K key, V value) {
            return new PairForMap<>(key, value);
        }

        public static <K, V> PairForMap<K, V> of(K key, V value) {
            return createKvPairForMap(key, value);
        }
    }

}
