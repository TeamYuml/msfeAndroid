package com.example.konrad.start_app.dbconections;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Klasa metod pomocniczych dla polaczen do bazy danych MYSQL
 * Created by Kaniak on 04.12.2017.
 */

public class Utility {

    /**
     * Metoda realizujeca polaczenie z web servicem
     * @param encodedParameters zakodowane paremtry do requesta POST
     * @param huc obiekt z polaczonym web servicem
     * @return String z wynikiem dzialania web servica
     */
    public String getResultFromWebService(String encodedParameters, HttpURLConnection huc) {
        try {
            OutputStream os = huc.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            // Kodawanie danych do wyslania przez http requesta
            String postData = encodedParameters;

            // wyslanie danych
            bw.write(postData);
            bw.flush();

            bw.close();
            os.close();

            InputStream is = huc.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

            String result = "";
            String line = "";

            // Pobranie wyniku zapytania
            while ((line = br.readLine()) != null) {
                result += line;
            }

            br.close();
            is.close();
            huc.disconnect();

            return result;
        } catch (IOException e) {
            return "Serwis czasowo niedostepny";
        }
    }

    /**
     * Metoda do otwarcia polaczenia
     * @param url String z url do ktorego otwieram polaczenie
     * @return Obiekt HttpURLConnection lub null gdy brak polaczenia
     */
    public HttpURLConnection getConnection(String url) {
        HttpURLConnection huc;
        try {
            URL ur = new URL(url);
            huc = (HttpURLConnection)ur.openConnection();
            if (huc.getResponseCode() == 200) {
                huc = (HttpURLConnection)ur.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                huc.setDoInput(true);
            }
        } catch (ProtocolException | MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return huc;
    }
}
