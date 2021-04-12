package com.example.meteodairy.core;

import android.app.Activity;
import android.app.Application;

import com.example.meteodairy.di.component.AppComponent;
import com.example.meteodairy.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class ApplicationMeteoDairy extends Application implements HasActivityInjector {
    public  static AppComponent appComponent;

    @Inject
    DispatchingAndroidInjector<Activity> injector;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().getContext(this).build();
        appComponent.inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return injector;
    }
}
