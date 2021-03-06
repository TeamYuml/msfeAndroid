package com.example.konrad.start_app.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.konrad.start_app.For_Login;
import com.example.konrad.start_app.R;
import com.example.konrad.start_app.dbconections.DatabaseConnection;

import java.util.concurrent.ExecutionException;

/**
 * Aktywnosc dla widoku rejestracji
 */
public class Main2Activity extends SameMethodsForLoginAndRegister {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sprawdzenie czy user jest juz zalogowany/zarejestrowny
        // jezeli tak to przechodze do kolejnego activity jezeli nie to
        // tworze widok rejestracji
        if (!super.checkIfUserIsLogged()) {
            setContentView(R.layout.activity_main2);

            ImageView imageReg = (ImageView) findViewById(R.id.imageview2);

            imageReg.setImageResource(R.drawable.primary_logo_on_transparent_282x69);
        } else {
            // polecenie ktore konczy activity
            // nie bedzie mozna kliknac back
            finish();
        }
    }

    /**
     * Metoda do obsługi przycisku rejestracji
     * @param view
     */
    public void register(View view) throws ExecutionException, InterruptedException {
        // Inicjalizacja odpowiednich EditTextow
        EditText em = (EditText)findViewById(R.id.email);
        EditText has = (EditText)findViewById(R.id.haslo);
        EditText hasConf = (EditText)findViewById(R.id.confHaslo);
        EditText im = (EditText)findViewById(R.id.imie);
        EditText naz = (EditText)findViewById(R.id.nazwisko);
        EditText pes = (EditText)findViewById(R.id.PESEL);
        EditText mia = (EditText)findViewById(R.id.miasto);
        EditText ul = (EditText)findViewById(R.id.ulica);
        EditText nrDomu = (EditText)findViewById(R.id.numerDomu);
        EditText tel = (EditText)findViewById(R.id.telefon);

        // Pobieranie wartosci z EditTextow
        String email = em.getText().toString().trim();
        String haslo = has.getText().toString().trim();
        String hasloConfirm = hasConf.getText().toString().trim();
        String imie = im.getText().toString().trim();
        String nazwisko = naz.getText().toString().trim();
        String pesel = pes.getText().toString().trim();
        String miasto = mia.getText().toString().trim();
        String ulica = ul.getText().toString().trim();
        String nrm = nrDomu.getText().toString().trim();
        String telefon = tel.getText().toString().trim();

        // parametry do przeslania dla polaczenia do DB
        String params[] = {"register", email, haslo,
                imie, nazwisko, pesel, miasto, ulica, nrm, telefon
        };

        // checkbox do przeczytanych zasad
        CheckBox checkbox = (CheckBox)findViewById(R.id.check2);

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
        // oraz sprawdzam czy checkbox zostal zaznaczony
        if (isFilled && checkbox.isChecked()) {
            // Sprawdzenie czy hasla sie zgadzaja
            if (haslo.compareTo(hasloConfirm) == 0) {
                // Walidacja emaila i numeru PESEL
                if (super.checkValidemail(email) && peselValid(pesel) && super.validPassword(haslo)) {
                    // Inicjalizacja nowego watku zwiazanego z polaczeniem do bazy danych MySQL
                    DatabaseConnection dc = new DatabaseConnection(Main2Activity.this);
                    // Start nowego watku
                    dc.execute(params);
                }
            } else {
                Toast.makeText(this, "Hasla sie nie zgadzaja", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Wypelnij wszystkie pola i przeczytaj zasady", Toast.LENGTH_SHORT).show();
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
     * Przejscia do activity z zasadami
     * @param view
     */
    public void goToRules(View view) {
        Intent intent = new Intent(this, For_Login.class);

        startActivity(intent);
    }
}