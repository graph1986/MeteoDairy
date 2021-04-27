package com.example.meteodairy.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.meteodairy.database.DayMeteoDao;
import com.example.meteodairy.models.DayMeteo;

@Database(entities = {DayMeteo.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract DayMeteoDao dayMeteoDao();
}
