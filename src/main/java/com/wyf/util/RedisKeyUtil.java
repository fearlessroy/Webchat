package com.wyf.util;

/**
 * Created by w7397 on 2017/3/30.
 */
public class RedisKeyUtil {
    private static String BIZ_EVENT = "EVENT";

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

}
