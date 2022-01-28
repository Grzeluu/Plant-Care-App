package com.grzeluu.plantcareapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static Long getTimestampForIncomingDate(Long current, int days) {
        return current + (long) days * 24 * 60 * 60 * 1000;
    }

    public static String getCurrentDate() {
        SimpleDateFormat ISO_8601_FORMAT =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        return ISO_8601_FORMAT.format(new Date());
    }
}
