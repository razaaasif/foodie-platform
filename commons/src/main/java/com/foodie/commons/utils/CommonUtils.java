package com.foodie.commons.utils;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
public class CommonUtils {
    private CommonUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Check if a String is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if a Collection (List, Set, etc.) is null or empty
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Check if a Map is null or empty
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Check if an array is null or empty
     */
    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Check if an object is null
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Check if an object is not null
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * Check the size of a Collection safely
     */
    public static int safeSize(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }

    /**
     * Check the size of a Map safely
     */
    public static int safeSize(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }

    /**
     * Check the length of an array safely
     */
    public static int safeLength(Object[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * Convert null string to empty string
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /**
     * Convert empty string to null
     */
    public static String emptyToNull(String str) {
        return (str != null && str.trim().isEmpty()) ? null : str;
    }
}