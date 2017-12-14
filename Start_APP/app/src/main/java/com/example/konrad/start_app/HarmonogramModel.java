package com.example.konrad.start_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.konrad.start_app.dbconections.SelectHarmonogram;

/**
 * Klasa sluzaca do stworzenia harmonogramu
 * Created by Kaniak on 05.12.2017.
 */

public class HarmonogramModel extends AppCompatActivity {

    int userID;

    String clickedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // String z kliknieta data w formacie rok-miesiac-dzien
        clickedDate = DateProperty.getInstance().date;

        // jezeli clickedDate jest null to wyswietlam komunikat i cofam
        // sie do poprzedniej activity
        if (clickedDate == null) {
            Toast.makeText(this, "Cos poszlo nie tak. Sprobuj jeszcze raz",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        // pobranie id usera
        userID = pobierzUserID();

        // jezelie id jest rowne zero oznacza ze nie poprawnie zostalo pobrane
        // z pliku shared preferences i zamykam activity
        if (userID == 0) {
            Toast.makeText(getBaseContext(), "Cos poszlo nie tak. Sprobuj jeszcze raz",
                    Toast.LENGTH_SHORT).show();
            finish();
        }

        pobierzDaneDoHarmonogramu();

        finish();
    }

    /**
     * Pobiera id zalogowanego usera
     * @return int id zalogowanego usera
     */
    private int pobierzUserID() {
        // Zmienna do przegladania pliku z preferencjami
        SharedPreferences sp = getSharedPreferences("com.example.konrad.start_app.preference_file",
                Context.MODE_PRIVATE);

        // pobranie i zwrocenie z pliku referencji id zalogowanego usera
        return sp.getInt("userID", 0);
    }

    /**
     * Pobieranie danych takich jak godzinia podania oraz nazwe leku
     * z web servica do harmonogramu
     */
    private void pobierzDaneDoHarmonogramu() {
        String params[] = {userID+"", clickedDate};

        SelectHarmonogram sh = new SelectHarmonogram(this);

        // select danych do stworzenia harmonogramu
        sh.execute(params);
    }
}
