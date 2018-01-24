package com.example.konrad.start_app.timetables;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konrad.start_app.R;
import com.example.konrad.start_app.timetables.DateProperty;

/**
 * Klasa z harmonogramem
 * pobieranym z dnia wybranego przez uzytkownika
 * z DatePickera
 */
public class Harmonogram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ustawianie widoku
        setContentView(R.layout.activity_harmonogram);

        // Pobranie tablicy z poprzedniego activity
        Object[] obj = (Object[]) getIntent().getExtras().getSerializable("daneDoHarmonogramu");

        if (obj == null) {
            finish();
        }

        // inicjalizacja tablicy stringow z dlugoscia tablicy pobranej z poprzedniego intenta
        String[][] daneDoHarmonogramu = new String[obj.length][2];

        // Dodanie wierszy z tablicy z poprzedniego intenta do nowej
        for (int i = 0; i < obj.length; i++) {
            daneDoHarmonogramu[i] = (String[]) obj[i];
        }

        TextView textView = (TextView)findViewById(R.id.name);
        textView.setText("Harmonogram na dzien: " + DateProperty.getInstance().date);

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll4);

        float textSize = 20;

        for (int i = 0; i < obj.length; i++) {
            TextView tv = new TextView(this);
            tv.setTextSize(textSize);
            tv.setPadding(0, 0, 0, 5);
            tv.setText("Godzina podania: " + daneDoHarmonogramu[i][0] + "\n"
                    + "Nazwa leku: " + daneDoHarmonogramu[i][1] + "\n");
            tv.setId(i);
            ll.addView(tv);
        }
    }

    public void makeCall(View view) {
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

    public void doSOS2(View view) {
        Intent intent = new Intent(this, Do_SOS.class);

        startActivity(intent);
    }
}



