package com.moringaschool.gamify;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCallInterface {
    @GET("/api/games")
    Call<GameSearchResponse> getData();
}
