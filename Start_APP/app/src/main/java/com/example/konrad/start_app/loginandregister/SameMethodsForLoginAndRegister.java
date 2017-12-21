package com.example.konrad.start_app.loginandregister;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Klasa parenta dla klas:
 * Main2Activity
 * Login
 * Zawiera metody uzywane w obu klasach
 * Created by Kaniak on 21.12.2017.
 */

public class SameMethodsForLoginAndRegister extends AppCompatActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("com.example.konrad.start_app.preference_file",
                Context.MODE_PRIVATE);
    }

    /**
     * Sprawdzenie czy user jest zalogowany
     * @return true jezeli jest false jezeli nie
     */
    protected boolean checkIfUserIsLogged() {
        // pobranie z pliku z preferencjami zmiennej o odpowiednim kluczu i podanie
        // wartosci domyslnej jezeli zmienna nie istnieje w pliku
        int isLogged = sp.getInt("isLogged", 0);

        // Sprawdzenie czy zmienna isLogged jest rowna 1 jezeli tak zwracam true
        // jezeli nie zwracam false
        return isLogged == 1 ? true : false;
    }

    /**
     * Dodaje uzytkownika do pliku sharedpreferences
     * jako zalogowanego oraz dodaje do niego jego id
     * @param result
     */
    protected void addUserToLogged(String result) {
        // Tworze zmienna do edytowanie pliku z referencjami
        SharedPreferences.Editor editor = sp.edit();

        // preferencja do sprawdzania czy usera jest juz zarejestrowany
        editor.putInt("isLogged", 1);

        // preferencja do trzymania userID
        editor.putInt("userID", Integer.parseInt(result));

        // zatwierdzenie zmian
        editor.commit();
    }

    //metoda sprawdza czy email nie jest nullem jezeli jest zwraca false
    protected boolean checkValidemail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Zly email", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Metoda sprawdzajaca czy haslo ma co najmniej osiem znakow
     * @param password - podane haslo
     * @return true jezeli haslo zgadza sie z patternem false jezeli nie
     */
    protected boolean validPassword(String password) {
        String pattern = ".{8,}?";
        if (password.matches(pattern)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Za krotkie haslo", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
