package com.example.konrad.start_app.timetables;

/**
 * Created by Kaniak on 08.12.2017.
 * Singleton dla przesylania daty pomiedzy aktywnosciami
 */

class DateProperty {
    private static final DateProperty ourInstance = new DateProperty();

    /**
     * data kliknieta z kalendarza
     * ustawiana w klasie Callendar
     */
    public String date;

    public static DateProperty getInstance() {
        return ourInstance;
    }

    private DateProperty() {
    }
}
