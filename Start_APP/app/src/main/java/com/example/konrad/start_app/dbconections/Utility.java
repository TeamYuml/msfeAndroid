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

    HttpURLConnection huc;

    /**
     * Metoda realizujeca polaczenie z web servicem
     * @param encodedParameters zakodowane paremtry do requesta POST
     * @return String z wynikiem dzialania web servica
     */
    public String getResultFromWebService(String encodedParameters) {
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

            return result;
        } catch (IOException e) {
            return "Serwis czasowo niedostepny";
        }
        finally {
            huc.disconnect();
        }
    }

    /**
     * Metoda do otwarcia polaczenia
     * @param url String z url do ktorego otwieram polaczenie
     * @return Obiekt HttpURLConnection lub null gdy brak polaczenia
     */
    public HttpURLConnection getConnection(String url) {
        try {
            URL ur = new URL(url);
            huc = (HttpURLConnection)ur.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoOutput(true);
            huc.setDoInput(true);
        } catch (ProtocolException | MalformedURLException e) {
            huc.setRequestProperty("isConnected", "0");
            return huc;
        } catch (IOException e) {
            huc.setRequestProperty("isConnected", "0");
            return huc;
        }
        huc.setRequestProperty("isConnected", "1");
        return huc;
    }
}
