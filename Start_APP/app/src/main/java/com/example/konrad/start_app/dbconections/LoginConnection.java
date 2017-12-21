package com.example.konrad.start_app.dbconections;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by Kaniak on 21.12.2017.
 */

public class LoginConnection extends AsyncTask<String, Void, String> {

    public LoginConnection() {}

    @Override
    protected String doInBackground(String... strings) {
        String url = "http://192.168.0.103/index.php/android/login";

        Utility utility = new Utility();

        HttpURLConnection huc = utility.getConnection(url);

        if (huc != null) {
            return utility.getResultFromWebService(encodeForLogin(strings), huc);
        } else {
            return "Serwis czasowo niedostepny";
        }
    }

    private String encodeForLogin(String... params) {
        String email = params[0];
        String password = params[1];

        String postData = "";

        try {
            postData = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                    + URLEncoder.encode("haslo", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return postData;
    }
}
