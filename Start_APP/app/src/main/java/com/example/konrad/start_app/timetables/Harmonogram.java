package com.example.konrad.start_app.timetables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

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
}



