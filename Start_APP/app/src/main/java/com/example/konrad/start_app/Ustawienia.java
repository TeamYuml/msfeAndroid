package com.example.konrad.start_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ustawienia extends AppCompatActivity {

    public EditText numberFC;
    public String saveNMB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);
        numberFC = (EditText) findViewById(R.id.numberFC);

        Button saveFD = (Button) findViewById(R.id.saveFC);
        saveFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveNMB = numberFC.getText().toString();

                SharedPreferences sp = getSharedPreferences("com.example.konrad.start_app.preference_file",
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("numerLekarza", saveNMB).commit();

                Toast.makeText(getBaseContext(),"Dodano numer", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Cofanie do activity mainscreena
     * @param view
     */
    public void powrot(View view) {
        finish();
    }

    @Override
    protected void onStart(){
        numberFC.setText(saveNMB);
        super.onStart();


    }
}
