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

public class Do_SOS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do__sos);
        Button btn = (Button) findViewById(R.id.sos2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sos = new Intent(Intent.ACTION_CALL);
                //z ustawien String
                sos.setData(Uri.parse("tel:123456789"));
                if (ActivityCompat.checkSelfPermission(Do_SOS.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(sos);
            }
        });
    }
}
