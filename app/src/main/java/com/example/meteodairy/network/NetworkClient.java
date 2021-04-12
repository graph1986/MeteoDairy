package com.example.meteodairy.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkClient {

    public ApiClient apiClient;

    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    public NetworkClient() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient transportClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        apiClient = new Retrofit.Builder()
                .client(transportClient)
                .baseUrl("https://www.gismeteo.ru/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiClient.class);
    }

}
