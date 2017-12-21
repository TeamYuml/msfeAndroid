package com.example.konrad.start_app.dbconections;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
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


    public DatabaseConnection() {}

    @Override
    protected String doInBackground(String... strings) {
        String url = "http://192.168.0.103/index.php/android/register";

        Utility utility = new Utility();

        HttpURLConnection huc = utility.getConnection(url);

        if (huc != null) {
            // Pobranie wyniku z webservica z pod wskazenego adresu url
            return utility.getResultFromWebService(encodeForRegister(strings), huc);
        } else {
            return "Serwis czasowo niedostepny";
        }
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
}
