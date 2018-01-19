package com.example.konrad.start_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity pozwalajace uzytkownikowi
 * ustawic telefon do lekarza rodzinnego
 */
public class Ustawienia extends AppCompatActivity {

    /**
     * EditText pozwalajacy na wpisanie numeru
     */
    public EditText numberFC;

    /**
     * Pobrany numer z EditTexta do zapisania
     */
    public String saveNMB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawienia);

        numberFC = (EditText) findViewById(R.id.numberFC);

        Button saveFD = (Button) findViewById(R.id.saveFC);
        saveFD.setOnClickListener(new View.OnClickListener() {
            /**
             * Metoda dla eventu klikniecia przycisku do zapisania numeru
             * w pliku sharedPreferences
             * @param view
             */
            @Override
            public void onClick(View view) {

                // pobranie numeru z EditTexta
                saveNMB = numberFC.getText().toString();

                // stworzenie obiektu pozwalajacego na dostep do pliku sharedPreferences
                SharedPreferences sp = getSharedPreferences("com.example.konrad.start_app.preference_file",
                        Context.MODE_PRIVATE);

                // stworzenie obiektu do edycji sharedPreferences
                SharedPreferences.Editor editor = sp.edit();

                // zapisanie do pliku danego numeru pod kluczem numerLekarza
                editor.putString("numerLekarza", saveNMB).commit();

                // wyswietlenie informacji dla uzytkownika
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
