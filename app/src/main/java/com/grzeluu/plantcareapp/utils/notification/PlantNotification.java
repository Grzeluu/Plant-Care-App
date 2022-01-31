package com.grzeluu.plantcareapp.utils.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.grzeluu.plantcareapp.R;

public class PlantNotification extends BroadcastReceiver {
    public static final String CHANNEL_ID = "Plants Channel";
    public static final String ID_EXTRA = "id_extra";
    public static final String TITLE_EXTRA = "title_extra";
    public static final String MESSAGE_EXTRA = "message_extra";

    public PlantNotification() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_flower)
                .setContentTitle(intent.getStringExtra(TITLE_EXTRA))
                .setContentTitle(intent.getStringExtra(MESSAGE_EXTRA))
                .build();

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(intent.getIntExtra(ID_EXTRA, 0), notification);
    }
}
