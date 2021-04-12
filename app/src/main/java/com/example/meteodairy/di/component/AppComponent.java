package com.example.meteodairy.di.component;

import android.content.Context;

import com.example.meteodairy.core.ApplicationMeteoDairy;
import com.example.meteodairy.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(ApplicationMeteoDairy app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder getContext(Context context);

        AppComponent build();
    }
}
