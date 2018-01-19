package com.example.konrad.start_app.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.sql.Date;
import java.util.List;

/**
 * DAO dla harmonogramu uzytkownika
 * Created by Kaniak on 11.12.2017.
 */

@Dao
public interface UserHarmonogramDAO {

    @Insert
    public void insertEvent(UserHarmonogramEntity uhe);

    @Delete
    public void deleteEvent(UserHarmonogramEntity uhe);

    @Query("SELECT * FROM userharmonogram WHERE :data BETWEEN dataStart " +
            "AND dataKoniec ORDER BY godzinaPodania ASC, nazwaLeku ASC ")
    public List<UserHarmonogramEntity> loadEvents(Date data);

    @Query("SELECT ostatnienotifId FROM userharmonogram ORDER BY ostatnienotifId DESC LIMIT 1")
    public int takeLastNotifId();
}
