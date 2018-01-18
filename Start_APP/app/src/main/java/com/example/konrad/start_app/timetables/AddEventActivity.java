package com.example.konrad.start_app.timetables;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.konrad.start_app.R;
import com.example.konrad.start_app.notifications.SetupNotification;
import com.example.konrad.start_app.room.RoomDB;
import com.example.konrad.start_app.room.UserHarmonogramEntity;

import java.sql.Date;
import java.util.Calendar;

/**
 * Activity dla dodania nowego eventu do harmonogramu
 * Created by Kaniak on 11.12.2017.
 */

public class AddEventActivity extends AppCompatActivity{

    /**
     * lista z godzinami podania
     */
    Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // godziny do wypelnienia listy
        Integer[] hours = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};

        // adapter dla spinnera posiadajacy context, layout oraz wartosci
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, hours);

        spinner = (Spinner)findViewById(R.id.spinner);

        // ustawianie adaptera dla danej listy
        spinner.setAdapter(adapter);

        // Pobieranie daty kliknietej przez uzytkownika
        String clickedDate = DateProperty.getInstance().date;
    }

    /**
     * Dodanie eventu do bazy danych
     * @param view
     */
    public void dodajEventToDB(View view) {
        // edit text z nazwa leku
        EditText et = (EditText)findViewById(R.id.editLek);
        // edit text z okresem trwania
        EditText et2 = (EditText)findViewById(R.id.editOkres);

        // pobranie nazwy leku
        String nazwaLeku = et.getText().toString().trim();
        // pobranie okresu trwania
        String okresTrwania = et2.getText().toString().trim();

        // sprawdzenie czy nazwa nie jest pusta
        if (nazwaLeku.compareTo("") == 0 && okresTrwania.compareTo("") == 0) {
            Toast.makeText(this, "Pola musza byc wypelnione", Toast.LENGTH_SHORT).show();
        } else {
            // pobranie godziny z spinnera
            int godzinaPodania = Integer.parseInt(spinner.getSelectedItem().toString());

            int okres = Integer.parseInt(okresTrwania);

            // utworzenie obiektu entity
            UserHarmonogramEntity uhe = new UserHarmonogramEntity();

            // setowanie w obiekcie nazwy leku
            uhe.setNazwaLeku(nazwaLeku);

            // setowanie w obiekcie godziny podania
            uhe.setGodzinaPodania(godzinaPodania);

            // setowanie w obiekcie daty
            uhe.setDataStart(Date.
                    valueOf(
                            DateProperty.
                            getInstance().
                            date
                    )
            );

            // stworzenie instancji kalendarza
            Calendar c = Calendar.getInstance();

            // stworzenie obiektu daty dla kliknietej przez uzytkownika daty
            Date data = Date.valueOf(DateProperty.getInstance().date);

            // ustawienie kalendarza na kliknieta date
            c.setTime(data);

            // pierwszy dzien pobierania leku
            int poczatkowyDzien = c.get(Calendar.DAY_OF_MONTH);

            // dodanie do kalendarza odpowiedniej ilosci dni aby
            // otrzymac date konca eventu
            c.add(Calendar.DATE, okres - 1);

            // setowanie koncowej daty
            uhe.setDataKoniec(new Date(c.getTimeInMillis()));

            // zbudowanie powiadomienia
            SetupNotification sN = new SetupNotification.NotifBuilder(this)
                    .content("Musisz pobrac: " + nazwaLeku + " o godzinie: " + godzinaPodania)
                    .period(okres)
                    .hour(godzinaPodania)
                    .startDay(poczatkowyDzien)
                    .build();

            // pobranie id powiadomienia
            int notifId = sN.scheduleNotification();

            // ustawienie id powiadomienia dla klasy entity
            uhe.setLastNotifId(notifId);

            // dodanie eventu do bazy danych
            addEvent(RoomDB.getDB(this), uhe);

            // skonczenie activity
            finish();

            // refresh activity z harmonogramem
            Intent intent = new Intent(this, UserHarmonogramActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    /**
     * Dodanie eventu do bazy danych
     * @param db - instancja bazy danych
     * @param uhe - obiekt do dodania
     */
    @WorkerThread
    private static void addEvent(final RoomDB db, UserHarmonogramEntity uhe) {
        db.userHarmonogramDAO().insertEvent(uhe);
    }
}
