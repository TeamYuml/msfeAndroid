package com.example.konrad.start_app;

import android.arch.persistence.room.TypeConverter;
import java.sql.Date;


/**
 * Convertery dla nie prostych typow danych
 * dla room bazy danych
 * Created by Kaniak on 11.12.2017.
 */

public class Converters {

    /**
     * Konwertuje date z timestampa w longu na Date
     * @param value timestamp do z konwertowania
     * @return
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Konwertuje z Date na timestamp w longu
     * @param date data z bazy danych
     * @return
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
