package com.example.konrad.start_app;

import android.arch.persistence.room.TypeConverter;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.Arrays;

/**
 * Created by Kaniak on 11.12.2017.
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
