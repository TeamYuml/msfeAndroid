package com.example.konrad.start_app.dbconections;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.konrad.start_app.loginandregister.Login;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;



/**
 * Klasa do obslugi bazy danych MYSQL wraz z PHP
 * Created by Kaniak on 17.11.2017.
 * @see android.os.AsyncTask
 */

public class DatabaseConnection extends AsyncTask<String, Void, String>{

    Context ctx;

    ProgressDialog dialog;

    public DatabaseConnection(Context ctx) {this.ctx = ctx; dialog = new ProgressDialog(ctx);}

    /**
     * Metoda wykonujaca sie przez
     * doInBackground uruchamiajaca
     * loading dialoga
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Please wait...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        // typ mówiący o żądaniu do wykonania
        String type = strings[0];

        if (type.compareTo("register") == 0) {
            String url = "http://192.168.0.107/index.php/android/register";

            Utility utility = new Utility();

            // wykonanie połączenia
            HttpURLConnection huc = utility.getConnection(url);

            // jezeli huc jest nullem to znaczy że response z jest inny od 200
            if (huc.getRequestProperty("isConnected").compareTo("1")  == 0) {
                // Pobranie wyniku z webservica z pod wskazenego adresu url
                return utility.getResultFromWebService(encodeForRegister(strings));
            } else {
                return "Serwis czasowo niedostepny";
            }
        } else {
            String url = "http://192.168.0.107/index.php/android/login";

            Utility utility = new Utility();

            // wykonanie połączenia
            HttpURLConnection huc = utility.getConnection(url);

            // jezeli huc jest nullem to znaczy że response z jest inny od 200
            if (huc.getRequestProperty("isConnected").compareTo("1")  == 0) {
                return utility.getResultFromWebService(encodeForLogin(strings));
            } else {
                return "Serwis czasowo niedostepny";
            }
        }
    }

    /**
     * Metoda wykonujaca sie po zwróceniu
     * stringa z doInBackground
     * ktora zamyka loading dialog
     * oraz sprawdza czy zostalo zwrocone id usera
     * jezeli nie pokazuje toasta z bledem z webservica
     * a jezeli tak to wracam do activity Login
     * z clerowaniem wszystkiego, co było wcześniej
     * i dodaje uzytkownika jako zalogowanego
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // zamkniecie dialoga
        dialog.dismiss();

        if (result.matches("\\d+")) {
            addUserToLogged(result);

            Intent intent = new Intent(ctx, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ctx.startActivity(intent);
        } else {
            Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Kodawanie dla post requesta
     * @param strings Tablica stringow z parametrami do kodowania
     * @return zakodowany String
     */
    private String encodeForRegister(String... strings) {
        String email = strings[1];
        String haslo = strings[2];
        String imie = strings[3];
        String nazwisko = strings[4];
        String pesel = strings[5];
        String adres = strings[6];

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
     * Kodowania dla post requesta dotyczacego logowania
     * @param params
     * @return
     */
    private String encodeForLogin(String... params) {
        String email = params[1];
        String password = params[2];

        String postData = "";

        try {
            postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                    + URLEncoder.encode("haslo", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return postData;
    }

    /**
     * Dodaje uzytkownika do pliku sharedpreferences
     * jako zalogowanego oraz dodaje do niego jego id
     * @param result
     */
    protected void addUserToLogged(String result) {
        SharedPreferences sp;

        sp = ctx.getSharedPreferences("com.example.konrad.start_app.preference_file",
                Context.MODE_PRIVATE);

        // Tworze zmienna do edytowanie pliku z referencjami
        SharedPreferences.Editor editor = sp.edit();

        // preferencja do sprawdzania czy usera jest juz zarejestrowany
        editor.putInt("isLogged", 1);

        // preferencja do trzymania userID
        editor.putInt("userID", Integer.parseInt(result));

        // zatwierdzenie zmian
        editor.commit();
    }
}
