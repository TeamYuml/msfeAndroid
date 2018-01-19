package com.example.konrad.start_app.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

/**
 * Tabela dla harmonogramu uzytkownika
 * Created by Kaniak on 11.12.2017.
 */

@Entity(tableName = "userharmonogram")
public class UserHarmonogramEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "godzinaPodania")
    public int godzinaPodania;

    @ColumnInfo(name = "nazwaLeku")
    public String nazwaLeku;

    @ColumnInfo(name = "dataStart")
    public Date dataStart;

    @ColumnInfo(name = "dataKoniec")
    public Date dataKoniec;

    @ColumnInfo(name = "ostatnienotifId")
    public int lastNotifId;

    public int getId() {
        return id;
    }

    public Date getDataKoniec() {return dataKoniec;}

    public Date getDataStart() {
        return dataStart;
    }

    public int getGodzinaPodania() {
        return godzinaPodania;
    }

    public String getNazwaLeku() {
        return nazwaLeku;
    }

    public int getLastNotifId(){ return lastNotifId; }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataKoniec(Date dataKoniec) {this.dataKoniec = dataKoniec;}

    public void setDataStart(Date dataStart) {
        this.dataStart = dataStart;
    }

    public void setGodzinaPodania(int godzinaPodania) {
        this.godzinaPodania = godzinaPodania;
    }

    public void setNazwaLeku(String nazwaLeku) {
        this.nazwaLeku = nazwaLeku;
    }

    public void setLastNotifId(int lastNotifId) { this.lastNotifId = lastNotifId; }
}
