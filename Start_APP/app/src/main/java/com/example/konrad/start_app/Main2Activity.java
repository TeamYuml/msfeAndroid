package com.example.konrad.start_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

import com.example.konrad.start_app.dbconections.DatabaseConnection;

/**
 * Aktywnosc dla widoku rejestracji
 */
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Sprawdzenie czy user jest juz zalogowany/zarejestrowny
        // jezeli tak to przechodze do kolejnego activity jezeli nie to
        // tworze widok rejestracji
        if (!checkIfUserIsLogged()) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            ImageView imageReg = (ImageView) findViewById(R.id.imageview1);

            imageReg.setImageResource(R.drawable.primary_logo_on_transparent_282x69);
        } else {
            super.onCreate(savedInstanceState);

            // Tworze przejscie do kolejnego activity
            Intent intent = new Intent(this, Callendar.class);

            // przechodze do kolejnego activity
            startActivity(intent);

            // polecenie ktore konczy activity
            // nie bedzie mozna kliknac back
            finish();
        }
    }

    /**
     * Metoda do obsługi przycisku rejestracji
     * @param view
     */
    public void register(View view)
    {
        // Inicjalizacja odpowiednich EditTextow
        EditText em = (EditText)findViewById(R.id.edit1);
        EditText has = (EditText)findViewById(R.id.edit2);
        EditText hasConf = (EditText)findViewById(R.id.edit3);
        EditText im = (EditText)findViewById(R.id.edit4);
        EditText naz = (EditText)findViewById(R.id.edit5);
        EditText pes = (EditText)findViewById(R.id.edit6);
        EditText adr = (EditText)findViewById(R.id.edit7);

        // Pobieranie wartosci z EditTextow
        String email = em.getText().toString().trim();
        String haslo = has.getText().toString().trim();
        String hasloConfirm = hasConf.getText().toString().trim();
        String imie = im.getText().toString().trim();
        String nazwisko = naz.getText().toString().trim();
        String pesel = pes.getText().toString().trim();
        String adres = adr.getText().toString().trim();

        String params[] = {email, haslo,
                imie, nazwisko, pesel, adres
        };

        // Zmienna sprawdzajaca czy wszystkie pola sa wypelnione
        boolean isFilled = true;

        // sprawdzanie wypelnienia pola
        for (String p: params) {
            if (p.equals("")) {
                isFilled = false;
                break;
            }
        }

        // Sprawdzenie czy zmienna jest na true jezeli jest na false to znaczy ze ktores pole jest puste
        if (isFilled) {
            // Sprawdzenie czy hasla sie zgadzaja
            if (haslo.compareTo(hasloConfirm) == 0) {
                // Walidacja emaila i numeru PESEL
                if (emailValid(email) && peselValid(pesel)) {
                    // Inicjalizacja nowego watku zwiazanego z polaczeniem do bazy danych MySQL
                    DatabaseConnection dc = new DatabaseConnection(this);
                    // Start nowego watku
                    dc.execute(params);

                    finish();
                }
            } else {
                Toast.makeText(this, "Hasla sie nie zgadzaja", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Wypelnij wszystkie pola", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metoda do walidacji adresu email
     * @param email Email do walidacji
     * @return True jezeli email sie zgadza False jezeli nie
     */
    protected boolean emailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(),"Zly email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Metoda sprawdzajaca poprawnosc numeru PESEL
     * @param pesel PESEL do walidacji
     * @return True jezeli pesel jest dobry false gdy nie jest
     */
    protected boolean peselValid(String pesel) {
        String peselPattern = "\\d{11}";

        if (pesel.matches(peselPattern)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Zly pesel", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Sprawdza czy uzytkownik byl wczesniej zarejestrowany/zalogowany
     * @return boolean
     */
    private boolean checkIfUserIsLogged() {

        // Utworzenie obiektu z preferencjami
        SharedPreferences sp = getSharedPreferences("com.example.konrad.start_app.preference_file",
                Context.MODE_PRIVATE);

        // pobranie z pliku z preferencjami zmiennej o odpowiednim kluczu i podanie
        // wartosci domyslnej jezeli zmienna nie istnieje w pliku
        int isLogged = sp.getInt("isLogged", 0);

        // Sprawdzenie czy zmienna isLogged jest rowna 1 jezeli tak zwracam true
        // jezeli nie zwracam false
        return isLogged == 1 ? true : false;
    }
}