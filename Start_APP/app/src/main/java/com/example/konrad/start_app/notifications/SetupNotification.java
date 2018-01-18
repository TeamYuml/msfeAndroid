package com.example.konrad.start_app.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.WorkerThread;
import android.support.v4.app.NotificationCompat;

import com.example.konrad.start_app.R;
import com.example.konrad.start_app.room.RoomDB;


import java.util.Calendar;

/**
 * Created by Kaniak on 27.12.2017.
 */
public class SetupNotification {

    Context ctx;

    /**
     * period - okres czasu przez, ktory ma byc pobierany lek
     */
    private int period;

    /**
     * content - zawartosc powiadomienia
     */
    private String content;

    /**
     * dzien pierwszego pobrania leku
     */
    private int startDay;

    /**
     * godzina pobrania leku
     */
    private int hour;

    public SetupNotification(NotifBuilder builder) {
        this.ctx = builder.ctx;
        this.period = builder.period;
        this.content = builder.content;
        this.startDay = builder.startDay;
        this.hour = builder.hour;
    }

    /**
     * Zaplnowanie notyfikacji dotyczacych danego leku
     */
    public int scheduleNotification() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        AlarmManager alarmManager;

        int notifnum = getLastNotifId(RoomDB.getDB(ctx));

        for (int i = 0; i < period; i++) {
            notifnum++;
            alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ctx, AlarmReceiver.class);
            intent.putExtra(AlarmReceiver.NOTIFICATION, getNotification());
            intent.putExtra(AlarmReceiver.ID,notifnum);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, notifnum,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            c.set(Calendar.DAY_OF_MONTH, startDay + i);
            c.set(Calendar.HOUR_OF_DAY, hour - 1);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 1);

            alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);
        }

        return notifnum;
    }

    /**
     * Zbudowanie powiadomienia
     * @return
     */
    public Notification getNotification() {
        Intent intent = new Intent(ctx, SetupNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, "userHarmonogram")
                .setSmallIcon(R.drawable.grayscale_logo_on_transparent_282x69)
                .setContentTitle("Pobranie leku")
                .setContentText(content)
                .setContentIntent(pendingIntent);

        return builder.build();
    }

    /**
     * Pobranie ostatniego uzywanego id do notyfikacji
     * @param db
     * @return
     */
    @WorkerThread
    private static int getLastNotifId(final RoomDB db) {
        int lastnotifID = db.userHarmonogramDAO().takeLastNotifId();

        return lastnotifID;
    }

    /**
     * Builder dla klasy SetupNotification
     */
    public static class NotifBuilder {
        Context ctx;

        private int period;
        private String content;
        private int startDay;
        private int hour;

        public NotifBuilder(Context ctx) {
            this.ctx = ctx;
        }

        public NotifBuilder period(int period) {
            this.period = period;
            return this;
        }

        public NotifBuilder content(String content) {
            this.content = content;
            return this;
        }

        public NotifBuilder startDay(int startDay) {
            this.startDay = startDay;
            return this;
        }

        public NotifBuilder hour(int hour) {
            this.hour = hour;
            return this;
        }

        public SetupNotification build() {
            return new SetupNotification(this);
        }
    }
}