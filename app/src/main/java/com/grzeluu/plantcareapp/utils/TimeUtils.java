package com.grzeluu.plantcareapp.utils;

import static com.grzeluu.plantcareapp.utils.Constants.TIME_DELAY;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'");

    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }

    public static Long getTimestampForNotification(Long current, int days) {
        return current + (long) days * 24 * 60 * 60 * 1000 + TIME_DELAY;
    }

    public static String getCurrentDate() {
        return DATE_FORMAT.format(new Date());
    }
}
