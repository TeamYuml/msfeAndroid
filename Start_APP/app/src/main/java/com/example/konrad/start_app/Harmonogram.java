package com.example.konrad.start_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll2);

        for (int i = 0; i < obj.length; i++) {
            TextView tv = new TextView(this);
            tv.setTextSize(20f);
            tv.setPadding(0,0,0, 5);
            tv.setText("Godzina podania: " + daneDoHarmonogramu[i][0] + "\n"
                    + "Nazwa leku: " + daneDoHarmonogramu[i][1] + "\n");
            tv.setId(i);
            ll.addView(tv);
        }

        /*Button calDFC = (Button) findViewById(R.id.button2);
        Button sos = (Button) findViewById(R.id.sos);
        calDFC.setOnClickListener(new View.OnClickListener() {
            @Override
            //Metoda która uruchamia na telefonie numer w postaci stringa ACTION_CALL automatycznie
            public void onClick(View v) {
                Intent callintent = new Intent(Intent.ACTION_CALL);
                //z ustawien String
                callintent.setData(Uri.parse("2238666658"));
                if (ActivityCompat.checkSelfPermission(Harmonogram.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(callintent);
            }

        });
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sos = new Intent(Intent.ACTION_CALL);
                //z ustawien String
                sos.setData(Uri.parse("2238666658"));
                if (ActivityCompat.checkSelfPermission(Harmonogram.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(sos);
            }
        }); */
    }
}



