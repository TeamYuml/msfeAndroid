package com.example.konrad.start_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.konrad.start_app.dbconections.LoginConnection;

import java.util.concurrent.ExecutionException;

/**
 * Klasa do logowania uzytkownika
 */
public class Login extends SameMethodsForLoginAndRegister {

    EditText emailedit;
    EditText passwordedit;

    //CheckBox check;

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
                // Czy potrzebny jest ten checkbox jak i tak user mial byc automatycznie logowany??
                // a i tak kazdy ma tylko jedno konto
                // bo kazdy ma tylko jeden pesel??
                //check = (CheckBox) findViewById(R.id.check1);
                //if (check.isChecked()) {
                //    emailedit.setText(email);
                //    passwordedit.setText(password);

                //}
                String params[] = {email, password};

                LoginConnection db = new LoginConnection();
                String result = db.execute(params)
                        .get();

                if (result.matches("\\d+")) {
                    super.addUserToLogged(result);

                    Intent intent = new Intent(this, MainScreenActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }
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