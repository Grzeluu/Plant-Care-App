package com.grzeluu.plantcareapp.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DaysUtils {
    static public int progressToDays(int frequency) {

        if (frequency >= 0 && frequency <= 30) {
            return frequency;
        } else if (frequency > 30 && frequency <= 35) {
            return (30 + (frequency - 30) * 5);
        } else {
            return (frequency - 34) * 30;
        }
    }

    static public int daysToProgress(int frequency) {

        if (frequency >= 0 && frequency <= 30) {
            return frequency;
        } else if (frequency > 30 && frequency <= 60) {
            return (30 + frequency / 5 - 6);
        } else {
            return ((frequency / 30) + 35);
        }
    }

    static public int daysFromLastAction(Date lastDate) {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - lastDate.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
