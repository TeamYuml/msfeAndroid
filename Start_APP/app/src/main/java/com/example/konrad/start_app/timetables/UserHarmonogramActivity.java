package com.example.konrad.start_app.timetables;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konrad.start_app.R;
import com.example.konrad.start_app.notifications.AlarmReceiver;
import com.example.konrad.start_app.notifications.SetupNotification;
import com.example.konrad.start_app.room.RoomDB;
import com.example.konrad.start_app.room.UserHarmonogramEntity;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Klasa dla harmonogramu uzytkownika
 * Created by Kaniak on 08.12.2017.
 */

public class UserHarmonogramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_harmonogram);

        // Pobieranie daty kliknietej przez uzytkownika
        String clickedDate = DateProperty.getInstance().date;

        TextView opis = (TextView) findViewById(R.id.name);
        opis.setText("Harmonogram na dzien "  + clickedDate);

        // pobieranie danych do harmonogramu
        List<UserHarmonogramEntity> spisEventow = getEvents(RoomDB.getDB(this), Date.valueOf(clickedDate));

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll4);

        float textSize = 24;

        // jezeli nie ma zadnego eventu w harmonogramie
        // pokazuje odpowiedni text jezeli sa wyswietlam eventy
        if (spisEventow.size() == 0) {
            TextView tv = new TextView(this);
            tv.setTextSize(24f);
            tv.setText("Brak harmonogramu na ten dzien");
            ll.addView(tv);
        } else {
            for (UserHarmonogramEntity uhe : spisEventow) {
                TextView tv = new TextView(this);
                tv.setTextSize(textSize);
                tv.setPadding(0, 0, 0, 5);
                tv.setText("Godzina podania: " + uhe.getGodzinaPodania() + "\n"
                        + "Nazwa leku: " + uhe.getNazwaLeku() + "\n");
                tv.setId(uhe.getId());
                ll.addView(tv);
                Button button = new Button(this);
                button.setText("Usun");
                // dodanie listenera do przyciska usuwania eventu z bazy danych
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        cancelNotification(uhe);

                        deleteEvent(RoomDB.getDB(getBaseContext()), uhe);
                        // ponowne tworzenie view
                        recreate();
                    }

                    // usuwanie eventu z bazy danych
                    @WorkerThread
                    private void deleteEvent(final RoomDB db, UserHarmonogramEntity uhe) {
                        db.userHarmonogramDAO().deleteEvent(uhe);
                    }
                });
                ll.addView(button);
            }
        }
    }

    /**
     * Metoda pobierajaca harmonogram z bazy danych
     * @param db - polaczenie do bazy danych
     * @param clickedDate - kliknieta data przez uzytkownika
     * @return
     */
    @WorkerThread
    private static List<UserHarmonogramEntity> getEvents(final RoomDB db, Date clickedDate) {
        List<UserHarmonogramEntity> spisEventow = db.userHarmonogramDAO().loadEvents(clickedDate);

        return spisEventow;
    }

    /**
     * Metoda dla buttona pozwalajaca na wyswietlenie
     * activity ktora dodaje event do harmonogramu
     * @param view
     */
    public void dodajEvent(View view) {
        Intent intent = new Intent(this, AddEventActivity.class);

        startActivity(intent);
    }

    /**
     * Zatrzymanie powiadomien gdy zdarzenie zostanie usuniete
     * @param uhe - zdarzenie do zatrzymania powiadomien
     */
    public void cancelNotification(UserHarmonogramEntity uhe) {
        int notifnum = uhe.getLastNotifId();

        Date dataStart = uhe.getDataStart();

        Date dataKoniec = uhe.getDataKoniec();

        long roznicaDat = dataKoniec.getTime() - dataStart.getTime();

        long iloscDniPobieraniaLeku = TimeUnit.DAYS.convert(roznicaDat, TimeUnit.MILLISECONDS) + 1;

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        for(int i = 0; i < iloscDniPobieraniaLeku; i++) {
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra(AlarmReceiver.ID,notifnum);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,

                    notifnum, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

            am.cancel(pendingIntent);

            pendingIntent.cancel();
            manager.cancel(notifnum);
            notifnum--;
        }
    }

    /**
     * Metoda dla buttona pozwalajaca na przejscie
     * do activity SOS
     * @param view
     */
    public void doSOS(View view) {
        Intent intent = new Intent(this, Do_SOS.class);

        startActivity(intent);
    }

    /**
     * Wykonuje polaczenie pod numer telefonu do
     * lekarza, ktory zostal wpisany w activity Ustawienia
     * @param view
     */
    public void wykonajPolaczenieZLekarzem(View view) {
        SharedPreferences sp = getSharedPreferences("com.example.konrad.start_app.preference_file",
                Context.MODE_PRIVATE);

        // pobranie numeru telefonu do lekarza
        String numerLpkZUstawien = sp.getString("numerLekarza", "Blad");

        if (numerLpkZUstawien.compareTo("Blad") != 0) {
            Intent callintent = new Intent(Intent.ACTION_CALL);
            //z ustawien String
            callintent.setData(Uri.parse("tel:"+numerLpkZUstawien));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            startActivity(callintent);
        } else {
            Toast.makeText(this, "Brak zapisanego numeru do lekarza.", Toast.LENGTH_SHORT).show();
        }
    }
}