package com.example.meteodairy.ui;

import android.content.Context;

import com.example.meteodairy.models.DayMeteo;

import java.util.Calendar;
import java.util.List;

public interface MainContract {
    interface View {
        void showError();

        void showWeather(List<DayMeteo> dayMeteoList,Calendar calendar);

        void showPickerYear(Calendar calendar);

        void showPickerMonth(Calendar calendar);

        void showPickerCity();

        void showDataEmpty(boolean b);

        void showProgressBar();
    }

    interface Presenter {
        void onStart(View view);

        void onStop();

        void onClickYear();

        void onClickMonth();

        void onClickCity();

        void changeYear(int year);

        void changeMonth(int month);

        void changeCity();
    }
}
