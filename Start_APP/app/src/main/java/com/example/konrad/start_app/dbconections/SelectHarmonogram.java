package com.example.konrad.start_app.dbconections;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.konrad.start_app.Harmonogram;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Klasa dla asynchronicznego zadania
 * ktore pobiera harmonogram z bazy danych
 * Created by Kaniak on 04.12.2017.
 */

public class SelectHarmonogram extends AsyncTask<String, Void, String> {

    String clickedDate;

    Context ctx;

    /**
     * Konstruktor ustawiajacy context
     * @param ctx Contenxt z ktorego zostal wywolany
     */
    public SelectHarmonogram(Context ctx) {this.ctx = ctx;}

    /**
     * Metoda wykonujaca sie przed rozpoczeciem
     * wykonywania sie zadania
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "http://192.168.2.42/index.php/android/getHarmonogram";

        Utility utility = new Utility();

        // Pobieranie harmonogramu z web service
        return utility.getResultFromWebService(encodeForHarmonogramSelect(strings), url);
    }

    /**
     * Kodowanie parametrów dla POST requesta
     * @param params parametry do kodowania
     * @return string z zakodowanymi parametrami
     */
    private String encodeForHarmonogramSelect(String... params) {
        String userID = params[0];
        clickedDate = params[1];

        String postData = "";

        try {
            postData = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8") + "&"
                    + URLEncoder.encode("clickedDate", "UTF-8") + "=" + URLEncoder.encode(clickedDate, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return postData;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result.compareTo("Brak harmonogramu") != 0) {
            // pomocnicza tabela do rozdzielenia na wiersze
            String temp[] = result.split(",");

            // tabela z danymi, ktore sa potrzebne do stworzenia harmonogramu
            String[][] informacjeDoZbudowaniaHarmonogramu = new String[temp.length][2];

            for (int i = 0; i < temp.length; i++) {
                // pomocnicza tabela do rozdzielenia z wierszy poszczegolnych danych
                String temp2[] = temp[i].split(":");

                informacjeDoZbudowaniaHarmonogramu[i][0] = temp2[0];
                informacjeDoZbudowaniaHarmonogramu[i][1] = temp2[1];
            }

            Intent intent = new Intent(ctx, Harmonogram.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("daneDoHarmonogramu", informacjeDoZbudowaniaHarmonogramu);
            intent.putExtras(bundle);
            intent.putExtra("Date", clickedDate);
            ctx.startActivity(intent);
        } else {
            Toast.makeText(ctx, result + " na ten dzien", Toast.LENGTH_SHORT).show();
        }
    }

}
