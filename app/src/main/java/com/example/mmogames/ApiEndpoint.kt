package com.example.mmogames

import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {
    @GET("games")
    fun getGames(): Call<List<GameDto>>
}