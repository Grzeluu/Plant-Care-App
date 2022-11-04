package com.grzeluu.plantcareapp.utils.notification;

import static com.grzeluu.plantcareapp.utils.TimeUtils.getTimestampForNotification;
import static com.grzeluu.plantcareapp.utils.notification.PlantNotification.CHANNEL_ID;
import static com.grzeluu.plantcareapp.utils.notification.PlantNotification.ID_EXTRA;
import static com.grzeluu.plantcareapp.utils.notification.PlantNotification.MESSAGE_EXTRA;
import static com.grzeluu.plantcareapp.utils.notification.PlantNotification.TITLE_EXTRA;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.grzeluu.plantcareapp.R;
import com.grzeluu.plantcareapp.base.App;
import com.grzeluu.plantcareapp.model.UserPlant;

public class NotificationUtils {
    public static void scheduleNotificationForPlant(UserPlant plant) {
        String title = App.getAppContext().getString(R.string.plant_notification_title);
        String message = App.getAppContext().getString(R.string.plant_notification_message, plant.getName());

        Intent intent = new Intent(App.getAppContext().getApplicationContext(), PlantNotification.class);
        intent.putExtra(MESSAGE_EXTRA, message);
        intent.putExtra(TITLE_EXTRA, title);
        intent.putExtra(ID_EXTRA, getNotificationID(plant.getId()));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                App.getAppContext().getApplicationContext(),
                getNotificationID(plant.getId()),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) App.getAppContext().getSystemService(Context.ALARM_SERVICE);

        int days = plant.getDaysToClosestAction();

        Long time = getTimestampForNotification(days);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public static void deleteNotification(Context context, String id){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(getNotificationID(id));
    }

    public static void createNotificationChannel(Context context) {
        String name = context.getString(R.string.notification_channel_name);
        String desc = context.getString(R.string.notification_channel_description);
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

    private static int getNotificationID(String id) {
        String subId = id.substring(id.length() - 13, id.length() - 4);
        return Integer.parseInt(subId);
    }
}
