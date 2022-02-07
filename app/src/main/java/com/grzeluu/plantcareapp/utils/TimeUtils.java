package com.grzeluu.plantcareapp.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'");

    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static Long getTimestampForNotification(int days) {
        return getTimestamp() + (long) (days) * 24 * 60 * 60 * 1000;
    }

    public static String getCurrentDate() {
        return DATE_FORMAT.format(new Date());
    }
}
