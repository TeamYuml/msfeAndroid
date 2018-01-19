package com.example.konrad.start_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.konrad.start_app.loginandregister.Login;

/**
 * Pierwsze okno po uruchomieniu aplikacji
 */
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONINT =1;
    public ImageView imageStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageStart = (ImageView) findViewById(R.id.imageview1);
        imageStart.setImageResource(R.drawable.primary_logo_on_transparent_282x69);

        // ustawienie urprawnien dla aplikacji
        getperm();

        // Pobranie z view layouta oraz utworznie onClick listenera
        LinearLayout xx = (LinearLayout)findViewById(R.id.glowny_lay);
        xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                //Przejscie do klasy rejestracji

                // Start nowej aktywnosci
                startActivity(intent);

                finish();

            }
        });
    }

    /**
     * Ustawia uprawnienia dla aplikacji
     */
    public void getperm()
    {
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},PERMISSIONINT );

        }

    }
}