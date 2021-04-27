package com.example.meteodairy.ui;

import android.content.Context;

import com.example.meteodairy.models.City;
import com.example.meteodairy.models.DayMeteo;

import java.util.Calendar;
import java.util.List;

public interface MainContract {
    interface View {
        void showError();

        void showWeather(List<DayMeteo> days,String city);

        void showPickerCity(List<City> cities);

        void showDataEmpty(boolean b);

        void showProgressBar();
    }

    interface Presenter {
        void onStart(View view);

        void onStop();

        void changeCity(City city);

        void onClickCity();
    }
}
