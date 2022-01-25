package com.grzeluu.plantcareapp.utils;

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
}
