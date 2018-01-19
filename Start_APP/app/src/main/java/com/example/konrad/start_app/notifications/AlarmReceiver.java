package com.example.konrad.start_app.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Alarm receiver dla notyfikacji
 * Created by Kaniak on 27.12.2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * notufikacja do pokazania
     */
    public static String NOTIFICATION = "notification";

    /**
     * id notyfikacji ktora ma zostac pokazana
     */
    public static String ID = "id";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        notification.flags = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;

        int id = intent.getIntExtra(ID, 0);

        nm.notify(id, notification);
    }
}