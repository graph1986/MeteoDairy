package com.example.meteodairy.modules;

import android.content.Context;

import com.example.meteodairy.tools.DBHelper;
import com.example.meteodairy.network.NetworkClient;
import com.example.meteodairy.tools.DaysParseHelper;
import com.example.meteodairy.ui.MainActivity;
import com.example.meteodairy.ui.MainContract;
import com.example.meteodairy.ui.MainPresenter;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class})
abstract public class AppModule {
    @Singleton
    @Provides
    public static DBHelper dbHelper(Context context) {
        return new DBHelper(context);
    }

    @Singleton
    @Binds
    abstract MainContract.Presenter presenter(MainPresenter presenter);

    @Provides
    @Singleton
    public static NetworkClient networkClient() {
        return new NetworkClient();
    }

    @Singleton
    @Provides
    public static DaysParseHelper daysParseHelper() {
        return new DaysParseHelper();
    }

    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity mainActivity();
}
