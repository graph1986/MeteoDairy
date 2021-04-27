package com.example.meteodairy.di.modules;

import android.content.Context;

import androidx.room.Room;

import com.example.meteodairy.database.AppDataBase;
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

    @Singleton
    @Provides
    public static AppDataBase appDataBase(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, "meteoDB").build();
    }

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

}
