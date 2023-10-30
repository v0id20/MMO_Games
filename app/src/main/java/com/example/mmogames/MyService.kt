package com.example.mmogames

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MyService(var gameAdapter: GameAdapter)  {
    val BASE_URL: String = "https://www.mmobomb.com/api1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val myEndpoint: ApiEndpoint = retrofit.create()

    private fun requestGamesList(callback: Callback<List<GameDto>>) {
        val call: Call<List<GameDto>> = myEndpoint.getGames()
        if (call != null) {
            call.enqueue(callback)
        }
    }

    fun getGamesList() {
        var callback: Callback<List<GameDto>> = object:  Callback<List<GameDto>> {

            override fun onResponse(call: Call<List<GameDto>>, response: Response<List<GameDto>>) {
                if (response.isSuccessful) {
                    gameAdapter.gamesArrayList = response.body()?: ArrayList<GameDto>()
                    gameAdapter.notifyDataSetChanged()
                } else {

                }
            }

            override fun onFailure(call: Call<List<GameDto>>, t: Throwable) {

                Log.e("this onFailure", "No reponse was received", t)
            }
        }
        requestGamesList(callback)
    }


}