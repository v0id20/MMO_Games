package com.example.mmogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoint {
    @GET("games")
    fun getGames(): Call<List<GameDto>>?

    @GET("game")
    fun getGameInfo(@Query("id") id: Int): Call<GameDto>?

    @GET("games")
    fun getFilteredAndAll(@Query("sort-by")  sortBy: String?, @Query("platform") platform: String?,  @Query("category")  vararg tag: String?): Call<List<GameDto>>?


}