package com.example.konrad.start_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class For_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for__login);
    }

    public void back(View view) {
        finish();
    }
}
