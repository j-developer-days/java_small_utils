package com.jdev.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtils {

    public static <K, V> boolean isNullOrEmpty(Map<K, V> kvMap) {
        return kvMap == null || kvMap.isEmpty();
    }

    public static <V> boolean isNullOrEmpty(Collection<V> vList) {
        return vList == null || vList.isEmpty();
    }

}
