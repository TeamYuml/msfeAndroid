package com.example.konrad.start_app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Klasa dla instancji bazy danych
 * Created by Kaniak on 11.12.2017.
 */
@Database(entities = {UserHarmonogramEntity.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB INSTANCE;

    public abstract UserHarmonogramDAO userHarmonogramDAO();

    public static RoomDB getDB(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "usermed")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
