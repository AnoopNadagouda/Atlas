package com.atlas.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isEmpty(java.util.Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(java.util.Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T> List<List<T>> partition(List<T> list, int chunkSize) {
        if (isEmpty(list) || chunkSize <= 0) return Collections.emptyList();
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += chunkSize) {
            partitions.add(new ArrayList<>(list.subList(i, Math.min(i + chunkSize, list.size()))));
        }
        return partitions;
    }
}
