package com.example.konrad.start_app.loginandregister;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.konrad.start_app.For_Login;
import com.example.konrad.start_app.MainScreenActivity;
import com.example.konrad.start_app.R;
import com.example.konrad.start_app.dbconections.DatabaseConnection;

import java.util.concurrent.ExecutionException;

/**
 * Klasa do logowania uzytkownika
 */
public class Login extends SameMethodsForLoginAndRegister {

    EditText emailedit;
    EditText passwordedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!super.checkIfUserIsLogged()) {
            setContentView(R.layout.activity_login);

            ImageView imageLog = (ImageView) findViewById(R.id.imageview1);
            imageLog.setImageResource(R.drawable.primary_logo_on_transparent_282x69);
        } else {
            Intent intent = new Intent(this, MainScreenActivity.class);

            startActivity(intent);

            finish();
        }
    }

    /**
     * Metoda dla przycisku sluzacego do zalogowania uzytkownika
     * @param view
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void procesLog(View view) throws ExecutionException, InterruptedException {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // sprawdzenie czy jest siec wifi
        if (networkInfo.isConnected()) {

            emailedit = (EditText)findViewById(R.id.emailvalid);
            passwordedit = (EditText)findViewById(R.id.passwordvalid);

            String email = emailedit.getText().toString().trim();
            String password = passwordedit.getText().toString().trim();

            if (super.checkValidemail(email) && super.validPassword(password)) {

                String params[] = {"login", email, password};

                DatabaseConnection db = new DatabaseConnection(Login.this);
                db.execute(params);
            }
        } else {
            Toast.makeText(this, "Brak sieci wifi", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metoda dla buttona pozwalajaca na przejscie do rejestracji
     * @param view
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(this, Main2Activity.class);

        startActivity(intent);
    }
}