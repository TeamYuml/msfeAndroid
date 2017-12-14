package com.example.konrad.start_app.dbconections;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.konrad.start_app.Callendar;


/**
 * Klasa do obslugi bazy danych MYSQL wraz z PHP
 * Created by Kaniak on 17.11.2017.
 * @see android.os.AsyncTask
 */

public class DatabaseConnection extends AsyncTask<String, Void, String>{

    Context ctx;

    private ProgressDialog mDialog;

    /**
     * Konstruktor ustawiajacy context
     * @param ctx Contenxt z ktorego zostal wywolany
     */
    public DatabaseConnection(Context ctx) {this.ctx = ctx;}

    /**
     * Metoda wykonujaca sie przed rozpoczeciem
     * wykonywania sie zadania
     */
    protected void onPreExecute() {
        super.onPreExecute();

        mDialog = new ProgressDialog(ctx);
        mDialog.setMessage("Prosze czekac...");
        mDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "http://192.168.0.101/index.php/android/register";

        Utility utility = new Utility();

        // Pobranie wyniku z webservica z pod wskazenego adresu url
        return utility.getResultFromWebService(encodeForRegister(strings), url);
    }

    /**
     * Kodawanie dla post requesta
     * @param strings Tablica stringow z parametrami do kodowania
     * @return zakodowany String
     */
    private String encodeForRegister(String... strings) {
        String email = strings[0];
        String haslo = strings[1];
        String imie = strings[2];
        String nazwisko = strings[3];
        String pesel = strings[4];
        String adres = strings[5];

        // Kodawanie danych do wyslania przez http requesta
        String postData = null;
        try {
            postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                    + URLEncoder.encode("haslo", "UTF-8") + "=" + URLEncoder.encode(haslo, "UTF-8") + "&"
                    + URLEncoder.encode("imie", "UTF-8") + "=" + URLEncoder.encode(imie, "UTF-8") + "&"
                    + URLEncoder.encode("nazwisko", "UTF-8") + "=" + URLEncoder.encode(nazwisko, "UTF-8") + "&"
                    + URLEncoder.encode("pesel", "UTF-8") + "=" + URLEncoder.encode(pesel, "UTF-8") + "&"
                    + URLEncoder.encode("adres", "UTF-8") + "=" + URLEncoder.encode(adres, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return postData;
    }

    /**
     * Metoda wykonujaca sie po skonczeniu zadania
     * Jezeli wszystko jest okay przechodzi do nowego activity
     * jezeli nie to zostaje w activity z ktorego zostalo wlaczone zadanie i wyswietla blad
     * @param result String z resultem zapytania
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // zamkniecie okienka dialogowego
        mDialog.dismiss();

        // Sprawdzenie czy wszystko sie udalo i dostalem id zarejestrowanego usera
        if (result.matches("\\d")) {
            // Otwieram plik z referencjami
            SharedPreferences sp = ctx.getSharedPreferences("com.example.konrad.start_app.preference_file",
                    Context.MODE_PRIVATE);

            // Tworze zmienna do edytowanie pliku z referencjami
            SharedPreferences.Editor editor = sp.edit();

            // preferencja do sprawdzania czy usera jest juz zarejestrowany
            editor.putInt("isLogged", 1);

            // preferencja do trzymania userID
            editor.putInt("userID", Integer.parseInt(result));

            // zatwierdzenie zmian
            editor.commit();

            /* Tworzenie obiektu intent do przejsia pomiedzy aktywnościami
            Parametry konstruktora:
            1. Klasa z ktorej zchodzimy
            2. Klasa do ktorej wchodzimy */
            Intent intent = new Intent(ctx, Callendar.class);

            // Start nowej aktywnosci
            ctx.startActivity(intent);
        } else {
            Toast.makeText(this.ctx, result, Toast.LENGTH_SHORT).show();
        }
    }
}
