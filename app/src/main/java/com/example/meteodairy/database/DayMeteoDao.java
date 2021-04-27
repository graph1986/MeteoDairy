package com.example.meteodairy.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.meteodairy.models.DayMeteo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface DayMeteoDao {

    @Query("SELECT * FROM (SELECT * FROM DayMeteo WHERE cityId=:city  ORDER BY id DESC  LIMIT 11) ORDER BY id")
    Flowable<List<DayMeteo>> getElevenDays(int city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DayMeteo> days);

}
