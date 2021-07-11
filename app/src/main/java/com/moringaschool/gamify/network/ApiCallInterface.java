package com.moringaschool.gamify.network;

import com.moringaschool.gamify.models.GameSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallInterface {
    @GET("/api/games")
    Call<GameSearchResponse> getGames(
            @Query("category") String category,
            @Query("term") String term
    );
}
