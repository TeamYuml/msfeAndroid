package com.example.konrad.start_app.loginandregister;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.Toast;

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

            ImageView imageReg = (ImageView) findViewById(R.id.imageview1);

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
                if (super.checkValidemail(email) && peselValid(pesel) && super.validPassword(haslo)) {
                    // Inicjalizacja nowego watku zwiazanego z polaczeniem do bazy danych MySQL
                    DatabaseConnection dc = new DatabaseConnection();
                    // Start nowego watku
                    String result = dc.execute(params)
                            .get();

                    // Sprawdzenie czy wszystko sie udalo i dostalem id zarejestrowanego usera
                    if (result.matches("\\d+")) {
                        super.addUserToLogged(result);
                        Intent intent = new Intent(this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                Toast.makeText(this, "Hasla sie nie zgadzaja", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Wypelnij wszystkie pola", Toast.LENGTH_SHORT).show();
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
}