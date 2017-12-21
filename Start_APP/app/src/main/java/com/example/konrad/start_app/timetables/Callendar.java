package com.example.konrad.start_app.timetables;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.konrad.start_app.R;

import java.util.Calendar;

/**
 * Klasa dla kalendarza z wyborem dni
 */
public class Callendar extends AppCompatActivity {

    DatePicker dp;

    /**
     * Zmienna przedstawiajaca typ harmonogramu
     * jezeli jest true to harmonogram lekarza
     * jezeli false harmonogram uzytkownika
     */
    Boolean type;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar);

        // nadanie booleana z poprzedniego activity
        type = getIntent().getBooleanExtra("typHarmonogramu",
                true);

        // pobranie DatePickera
        dp = (DatePicker)findViewById(R.id.datePicker);

        // pobranie kalendzarza
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // wylacznie przeszłych dni z DatePickera
        dp.setMinDate(System.currentTimeMillis() - 1000);

        // inicjalizacja Datepickera odpowienia data oraz
        // oraz dodanie do niego onDateChangeListenera
        // ktory posiada metode onDateChange()
        dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        // pobieram date ktora zostala kliknieta przez uzytkownika
                        String clickedDate = dp.getYear() + "-" + (dp.getMonth() + 1) +  "-" + dp.getDayOfMonth();

                        // sprawdzenie typu żadania
                        // jezeli true to harmonogram lekarza
                        // jezeli false to harmonogram usera
                        if (type) {
                            // Tworze nowe activity wysylajac do niego kliknieta date
                            Intent intent = new Intent(getBaseContext(), HarmonogramModel.class);
                            DateProperty.getInstance().date = clickedDate;
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getBaseContext(), UserHarmonogramActivity.class);
                            DateProperty.getInstance().date = clickedDate;
                            startActivity(intent);
                        }
                    }
                });
    }
}
