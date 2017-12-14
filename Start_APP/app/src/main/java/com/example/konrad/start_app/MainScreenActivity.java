package com.example.konrad.start_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Activity dla wyboru typu harmonogramu
 * lekarza lub uzytkownika
 * Created by Kaniak on 08.12.2017.
 */

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    /**
     * Obsluga przycisku do opcji pokazania harmonogramu lekarza
     * @param view
     */
    public void doLekarza(View view) {
        Intent intent = new Intent(this, Callendar.class);
        intent.putExtra("typHarmonogramu", true);
        startActivity(intent);
    }

    /**
     * Obsługa przycisku do pokazania harmonogramu uzytkownika
     * @param view
     */
    public void doUsera(View view) {
        Intent intent = new Intent(this, Callendar.class);
        intent.putExtra("typHarmonogramu", false);
        startActivity(intent);
    }

    /**
     * Obsluga przycisku pokazujacego ustawienia
     * @param view
     */
    public void doUstawien(View view) {
        Intent intent = new Intent(this, Ustawienia.class);
        startActivity(intent);
    }
}
