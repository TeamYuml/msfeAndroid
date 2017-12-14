package com.example.konrad.start_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView imageLog = (ImageView)findViewById(R.id.imageview1);
        imageLog.setImageResource(R.drawable.primary_logo_on_transparent_282x69);
    }
    public void procesLog(View view)
    {

    }
}
