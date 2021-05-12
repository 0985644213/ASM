package com.example.asm.network;

import com.example.asm.model.Weather;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiManager {
    public static String BASE_URL = "https://developer.accuweather.com";


    @GET("/forecasts/v1/hourly/12hour/353412?apikey=cx78OwkRg2RTOYiAk6nUK37rs7ViQN2h&language=vi-vn&metric=true")
    Call<List<Weather>> gethour();

}
