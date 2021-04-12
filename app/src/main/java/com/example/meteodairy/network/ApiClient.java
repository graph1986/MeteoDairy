package com.example.meteodairy.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ApiClient {
    @GET("diary/4364/{year}/{month}")
    Single<String> loadDiary(@Path("year")String year, @Path("month")String month);

    @GET("weather/4364/{year}/{month}")
    Single<String> loadWeather(@Path("year")String year, @Path("month")String month);
}
