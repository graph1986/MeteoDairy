package com.example.meteodairy.ui;

import com.example.meteodairy.models.DayMeteo;
import com.example.meteodairy.tools.DBHelper;
import com.example.meteodairy.network.NetworkClient;
import com.example.meteodairy.tools.DaysParseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter implements MainContract.Presenter {
    private NetworkClient networkClient;
    private MainContract.View view;
    private Disposable dairyDisposable;
    private Calendar calendar;
    private String year;
    private String month;
    private String city;
    private Disposable dbDisposable;

    @Inject
    DaysParseHelper daysParseHelper;

    @Inject
    DBHelper dbHelper;

    @Inject
    public MainPresenter(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }

    @Override
    public void onStart(MainContract.View view) {
        this.view = view;
        if (city == null) {
            city = "4364";
        }
        calendar = Calendar.getInstance();
        if (year == null) {
            year = new SimpleDateFormat("y").format(calendar.getTime());
        }
        if (month == null) {
            month = new SimpleDateFormat("MM").format(calendar.getTime());
        }
        loadDairy(year, month);
    }

    @Override
    public void onStop() {
        this.view = null;
        if (dairyDisposable != null) {
            dairyDisposable.dispose();
        }
        if (dbDisposable!=null){
            dbDisposable.dispose();
        }

    }

    @Override
    public void onClickYear() {
        view.showPickerYear(calendar);
    }

    @Override
    public void onClickMonth() {
        view.showPickerMonth(calendar);
    }

    @Override
    public void onClickCity() {
        view.showPickerCity();
    }

    @Override
    public void changeYear(int yearChanged) {
        year = String.valueOf(yearChanged);
        calendar.set(Calendar.YEAR, yearChanged);
        getDays(year,month);
        loadDairy(year, month);
    }

    @Override
    public void changeMonth(int monthChanged) {
        month = String.valueOf(monthChanged + 1);
        calendar.set(Calendar.MONTH, monthChanged);
        if (calendar.getTime().before(Calendar.getInstance().getTime())) {
            getDays(year,month);
            loadDairy(year, month);
        } else {
            view.showDataEmpty(true);
        }
    }

    @Override
    public void changeCity() {
    }
private void getDays(String year, String month){
    view.showProgressBar();
    dbDisposable=dbHelper.getDays(year,month).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BiConsumer<List<DayMeteo>, Throwable>() {
                @Override
                public void accept(List<DayMeteo> dayMeteoList, Throwable throwable) throws Exception {
                    if (throwable!=null){
                        view.showError();
                    }else{
                        view.showWeather(dayMeteoList, calendar);
                    }
                }
            });
}
    private void loadDairy(String year, String month) {
        view.showProgressBar();
        dairyDisposable = networkClient.apiClient.loadDiary(year, month).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            view.showError();
                        } else {
                            List<DayMeteo> days = daysParseHelper.parse(s, year, month);
                            view.showDataEmpty(days.isEmpty());
                            if (!days.isEmpty()) {
                                dbHelper.storeDays(days);
                                getDays(year,month);
                            }
                        }
                    }
                });
    }

}
