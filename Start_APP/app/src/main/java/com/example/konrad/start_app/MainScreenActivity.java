package com.example.konrad.start_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.konrad.start_app.timetables.Callendar;

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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // sprawdzenie czy jest siec wifi
        if (networkInfo.isConnected()) {
            Intent intent = new Intent(this, Callendar.class);
            intent.putExtra("typHarmonogramu", true);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Aby uzyc tej funkcji potrzebny jest dostep do internetu",
                    Toast.LENGTH_SHORT).show();
        }
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
