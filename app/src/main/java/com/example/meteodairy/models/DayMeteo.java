package com.example.meteodairy.models;

import android.widget.ImageView;

public class DayMeteo {
   private String numberDay;
   private String temperature;
   private String urlCloud;
   private String urlEffect;
   private String year;

    public String getId() {
        return year+month+numberDay;
    }

    private String id;
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private String month;

    public DayMeteo(String numberDay, String temperature, String urlCloud, String urlEffect, String year, String month) {
        this.numberDay = numberDay;
        this.temperature = temperature;
        this.urlCloud = urlCloud;
        this.urlEffect = urlEffect;
        this.year = year;
        this.month = month;
    }

    public String getNumberDay() {
        return numberDay;
    }

    public void setNumberDay(String numberDay) {
        this.numberDay = numberDay;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUrlCloud() {
        return urlCloud;
    }

    public void setUrlCloud(String urlCloud) {
        this.urlCloud = urlCloud;
    }

    public String getUrlEffect() {
        return urlEffect;
    }

    public void setUrlEffect(String urlEffect) {
        this.urlEffect = urlEffect;
    }
}
