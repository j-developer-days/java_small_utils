package com.jdev.util;

import com.jdev.exceptions.HelperException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapUtilsTest {

    @Test
    void test_createAndFillMap() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Real", 1950);
        expected.put("Barcelona", 1983);
        Map<String, Integer> actual = MapUtils.createAndFillMap(new String[]{"Real", "Barcelona"}, new Integer[]{1950, 1983});
        assertEquals(expected, actual);
    }

    @Test
    void test_createAndFillMap_throwException_2arraysNotTheSameSize() {
        HelperException helperException = assertThrows(HelperException.class, () -> MapUtils.createAndFillMap(new String[]{"Real"},
                new Integer[]{1950, 1983}));
        Assertions.assertTrue(helperException.getMessage().startsWith("Can't fill Map, because keys length is"));
    }

    @Test
    void test_createAndFillMap_withPair() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Real", 1950);
        expected.put("Barcelona", 1983);
        Map<String, Integer> actual = MapUtils.createAndFillMap(new MapUtils.PairForMap[]{new MapUtils.PairForMap<>("Real", 1950),
                new MapUtils.PairForMap<>("Barcelona", 1983)});
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test_createAndFillMap_withList(){
        Map<Integer, Double> actual = MapUtils.createAndFillMap(List.of(MapUtils.PairForMap.createKvPairForMap(100, 50.292),
                MapUtils.PairForMap.of(200, 25.190)));
        Assertions.assertEquals(Map.of(100, 50.292, 200, 25.190), actual);
    }

}