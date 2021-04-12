package com.example.meteodairy.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.meteodairy.models.DayMeteo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.http.GET;

public  class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final int VERSION = 1;

    public DBHelper(@Nullable Context context) {
        super(context, "meteoDB", null, VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table dayMeteoTable(" +
                "id text primary key ," +
                "year text," +
                "month text," +
                "numberDay text," +
                "temperature text," +
                "urlCloud text," +
                "urlEffect text" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeDays(List<DayMeteo> days) {
        for (DayMeteo dayMeteo : days) {
            if (!isDayExists(dayMeteo)) {
                saveDay(dayMeteo);
            }
        }

    }

    private boolean isDayExists(DayMeteo day) {
        Cursor cursor = db.query("dayMeteoTable", null, "id=?", new String[]{day.getId()}, null, null, null);
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    public  Single<List<DayMeteo>> getDays(String year, String month){
        return Single.create(new SingleOnSubscribe<List<DayMeteo>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<DayMeteo>> emitter) throws Exception {
                Cursor cursor = db.query("dayMeteoTable", null, "year=?" + " AND " + "month=?", new String[]{year, month}, null, null, null);
                List<DayMeteo> dayMeteoList = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    do {
                        String numberDay = cursor.getString(cursor.getColumnIndex("numberDay"));
                        String temperature = cursor.getString(cursor.getColumnIndex("temperature"));
                        String urlCloud = cursor.getString(cursor.getColumnIndex("urlCloud"));
                        String urlEffect = cursor.getString(cursor.getColumnIndex("urlEffect"));
                        DayMeteo dayMeteo = new DayMeteo(numberDay, temperature, urlCloud, urlEffect, year, month);
                        dayMeteoList.add(dayMeteo);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                emitter.onSuccess(dayMeteoList);
            }
        });


    }

    private void saveDay(DayMeteo dayMeteo) {
        ContentValues cv = new ContentValues();
        cv.put("id", dayMeteo.getId());
        cv.put("year", dayMeteo.getYear());
        cv.put("month", dayMeteo.getMonth());
        cv.put("numberDay", dayMeteo.getNumberDay());
        cv.put("temperature", dayMeteo.getTemperature());
        cv.put("urlCloud", dayMeteo.getUrlCloud());
        cv.put("urlEffect", dayMeteo.getUrlEffect());
        db.insert("dayMeteoTable", null, cv);
    }
}
