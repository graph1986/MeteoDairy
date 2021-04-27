package com.example.meteodairy.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class DayMeteo {
    private int numberDay;
    private String temperature;
    private String urlCloud;
    private String urlEffect;
    private int month;
    private int year;
    private int cityId;

    @NonNull
    public long getId() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month,numberDay);
        return calendar.getTimeInMillis();
    }

    @PrimaryKey
    @NonNull
    public long id;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public DayMeteo(int cityId, int numberDay, String temperature, String urlCloud, String urlEffect, int year, int month) {
        this.numberDay = numberDay;
        this.temperature = temperature;
        this.urlCloud = urlCloud;
        this.urlEffect = urlEffect;
        this.year = year;
        this.month = month;
        this.cityId = cityId;
        this.id=getId();
    }

    public int getNumberDay() {
        return numberDay;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getUrlCloud() {
        return urlCloud;
    }

    public String getUrlEffect() {
        return urlEffect;
    }

    public int getCityId() {
        return cityId;
    }

}
