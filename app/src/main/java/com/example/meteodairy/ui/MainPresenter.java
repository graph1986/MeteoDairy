package com.example.meteodairy.ui;

import com.example.meteodairy.database.AppDataBase;
import com.example.meteodairy.models.City;
import com.example.meteodairy.models.DayMeteo;
import com.example.meteodairy.network.NetworkClient;
import com.example.meteodairy.tools.DaysParseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private int year;
    private int month;
    private int pastMonth;
    private List<City> cities;
    private City city;
    private CompositeDisposable compositeDisposable;
    private int pastYear;

    @Inject
    AppDataBase appDataBase;

    @Inject
    DaysParseHelper daysParseHelper;

    @Inject
    NetworkClient networkClient;

    @Inject
    public MainPresenter() {
    }


    @Override
    public void onStart(MainContract.View view) {
        this.view = view;
        createCityList();
        compositeDisposable = new CompositeDisposable();
        city = cities.get(3);
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        pastYear=calendar.get(Calendar.YEAR);
        pastMonth = calendar.get(Calendar.MONTH) + 1;
        getDays(year, month);
    }

    @Override
    public void onStop() {
        this.view = null;
        compositeDisposable.dispose();
    }

    @Override
    public void changeCity(City city) {
        this.city = city;
        compositeDisposable.dispose();
        compositeDisposable = new CompositeDisposable();
        getDays(year, month);
    }

    @Override
    public void onClickCity() {
        view.showPickerCity(cities);
    }


    private void getDays(int year, int month) {
        view.showProgressBar();
        Disposable dbDisposable = appDataBase.dayMeteoDao().getElevenDays(city.getId()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DayMeteo>>() {
                    @Override
                    public void accept(List<DayMeteo> dayMeteoList) throws Exception {
                        if (!dayMeteoList.isEmpty()) {
                            view.showWeather(dayMeteoList, city.getName());
                        }
                        if (dayMeteoList.size() > 10) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DAY_OF_MONTH, -5);
                            int lastFiveDay = calendar.get(Calendar.DAY_OF_MONTH);
                            calendar.add(Calendar.DAY_OF_MONTH, +10);
                            int futureFiveDay = calendar.get(Calendar.DAY_OF_MONTH);
                            if (dayMeteoList.get(0).getNumberDay() == lastFiveDay && dayMeteoList.get(10).getNumberDay() == futureFiveDay) {
                                view.showWeather(dayMeteoList, city.getName());
                            } else if (dayMeteoList.get(10).getNumberDay() != futureFiveDay) {
                                loadWeather(year, month);
                            } else if (dayMeteoList.get(0).getNumberDay() != lastFiveDay) {
                                loadDairy(year, month);
                            }
                        } else {
                            if (pastMonth==Calendar.DECEMBER){
                               loadDairy(pastYear,pastMonth);
                            }else{
                                loadDairy(year, pastMonth);
                            }
                        }
                    }
                });
        compositeDisposable.add(dbDisposable);
    }

    private void loadDairy(int year, int month) {
        Disposable dairyDisposable = networkClient.apiClient.loadDiary(year, month, city.getId()).subscribeOn(Schedulers.io())
                .subscribe(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            view.showError();
                        } else {
                            List<DayMeteo> days = daysParseHelper.parseDiary(s, city.getId(), year, month);
                            if (!days.isEmpty()) {
                                appDataBase.dayMeteoDao().insertAll(days);
                            }
                        }
                    }
                });
        compositeDisposable.add(dairyDisposable);
    }

    private void loadWeather(int year, int month) {
        Disposable weatherDisposable = networkClient.apiClient.loadWeather(city.getUrlName(), city.getId()).subscribeOn(Schedulers.io())
                .subscribe(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            view.showError();
                        } else {
                            List<DayMeteo> days = daysParseHelper.parseWeather(s, city.getId(), year, month);
                            if (!days.isEmpty()) {
                                appDataBase.dayMeteoDao().insertAll(days);
                            }
                        }
                    }
                });
        compositeDisposable.add(weatherDisposable);
    }

    private void createCityList() {
        City kazan = new City(4364, "-kazan-", "Казань");
        City krasnysteklovar = new City(201904, "-krasny-steklovar-", "Красный-Стекловар");
        City kukeyevo = new City(205398, "-kukeyevo-", "Кукеево");
        City tatarskiyesaraly = new City(205002, "-tatarskiye-saraly-", "Татарские Саралы");
        City ilet = new City(201468, "-ilet-", "Илеть");
        City shali = new City(205367, "-shali-", "Шали");
        City matyushino = new City(204985, "-matyushino-", "Матюшино");
        City dubyazy = new City(204605, "-dubyazy-", "Дубъязы");
        City bima = new City(204970, "-bima-", "Бима");
        City oktyabrsky = new City(204568, "-oktyabrsky-", "Октябрьский");
        cities = new ArrayList<>();
        cities.add(kazan);
        cities.add(krasnysteklovar);
        cities.add(kukeyevo);
        cities.add(tatarskiyesaraly);
        cities.add(ilet);
        cities.add(shali);
        cities.add(matyushino);
        cities.add(dubyazy);
        cities.add(bima);
        cities.add(oktyabrsky);
    }
}
