package com.lyt.netstate.library.utils;

public class Nulls {

    /**
     *空检查 若为空则抛出异常
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    /**
     * 空检查 若为空则抛出异常
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NullPointerException(message);
        return obj;
    }

}
