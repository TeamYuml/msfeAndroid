package com.example.konrad.start_app;

import com.example.konrad.start_app.dbconections.DatabaseConnection;

import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Kaniak on 17.11.2017.
 */

public class RegisterTest {

    Main2Activity m2a;
    DatabaseConnection dc;

    @Test
    public void emailValidationWhenItsGoodReturnTrue() {
        m2a = new Main2Activity();
        assertEquals(m2a.emailValid("lol@wp.pl"), true);
    }

    @Test
    public void emailValidationWhenItsWrongReturnFalse() {
        m2a = mock(Main2Activity.class);
        assertEquals(m2a.emailValid("lol"), false);
    }

    @Test
    public void peselValidationWhenPeselIsGood() {
        m2a = new Main2Activity();
        assertEquals(m2a.peselValid("12345678901"), true);
    }

    @Test
    public void peselValidationWhenPeselHasNotEnoughDigits() {
        m2a = mock(Main2Activity.class);
        assertEquals(m2a.peselValid("1234567890"), false);
    }

    @Test
    public void peselValidationWhenPeselHasLetters() {
        m2a = mock(Main2Activity.class);
        assertEquals(m2a.peselValid("1234567890s"), false);
    }

    @Test
    public void peselValidationWhenPeselHasLettersAndNotEnoughDigits() {
        m2a = mock(Main2Activity.class);
        assertEquals(m2a.peselValid("123456780s"), false);
    }
}
