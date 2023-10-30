package com.example.mmogames

import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {
    @GET("games") // Replace with your endpoint
    fun getGames(): Call<List<GameDto>> // Replace ResponseType with the type of response you expect
}