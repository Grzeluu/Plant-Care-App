package com.grzeluu.plantcareapp.utils;

import static com.grzeluu.plantcareapp.utils.PlantNotification.CHANNEL_ID;
import static com.grzeluu.plantcareapp.utils.PlantNotification.ID_EXTRA;
import static com.grzeluu.plantcareapp.utils.PlantNotification.MESSAGE_EXTRA;
import static com.grzeluu.plantcareapp.utils.PlantNotification.TITLE_EXTRA;
import static com.grzeluu.plantcareapp.utils.TimeUtils.getTimestamp;
import static com.grzeluu.plantcareapp.utils.TimeUtils.getTimestampForIncomingDate;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.grzeluu.plantcareapp.model.UserPlant;

public class NotificationUtils {
    public static void scheduleNotificationForPlant(Context context, String title, String message, UserPlant plant) {
        String subId = plant.getId();
        subId = subId.substring(subId.length() - 13, subId.length() - 4);

        Intent intent = new Intent(context.getApplicationContext(), PlantNotification.class);
        intent.putExtra(MESSAGE_EXTRA, message);
        intent.putExtra(TITLE_EXTRA, title);
        intent.putExtra(ID_EXTRA, subId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(),
                Integer.parseInt(subId),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long current = getTimestamp();

        int days = plant.getDaysToClosestAction();

        Long time = getTimestampForIncomingDate(current, days);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public static void createNotificationChannel(Context context) {
        String name = "Plant Notifications";
        String desc = "Notifications for plants needs";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    name,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(desc);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
