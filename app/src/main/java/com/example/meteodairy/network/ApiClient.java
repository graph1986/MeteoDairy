package com.example.meteodairy.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClient {
    @GET("diary/{city}/{year}/{month}")
    Single<String> loadDiary(@Path("year")Integer year, @Path("month")Integer month,@Path("city")Integer city);

    @GET("weather{nameCity}{city}/10-days/")
    Single<String> loadWeather(@Path("nameCity")String nameCity,@Path("city")Integer city);
}
