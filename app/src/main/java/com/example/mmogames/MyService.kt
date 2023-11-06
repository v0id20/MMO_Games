package com.example.mmogames

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MyService() {
    val BASE_URL: String = "https://www.mmobomb.com/api1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofit2: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GameInfoConerterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val myEndpoint: ApiEndpoint = retrofit.create()

    private val myEndpoint2: ApiEndpoint = retrofit2.create()

    private fun requestGamesList(callback: Callback<List<GameDto>>) {
        val call: Call<List<GameDto>>? = myEndpoint.getGames()
        call?.enqueue(callback)
    }

    private fun requestGameInfo(callback: Callback<GameDto>, gameId: Int) {
        val call: Call<GameDto>? = myEndpoint2.getGameInfo(gameId)
        call?.enqueue(callback)
    }

    fun getGamesList(liveData: MutableLiveData<LoadingStatus>) {
        val callback: Callback<List<GameDto>> = object : Callback<List<GameDto>> {

            override fun onResponse(call: Call<List<GameDto>>, response: Response<List<GameDto>>) {
                if (response.isSuccessful) {
                    liveData.setValue(LoadingStatus.Success(response.body()))
                } else {
                    liveData.setValue(LoadingStatus.Error("Response unsuccessful"))
                    Log.d("onResponse", "Response unsuccessful")
                }
            }

            override fun onFailure(call: Call<List<GameDto>>, t: Throwable) {
                liveData.setValue(LoadingStatus.Error(t.message.toString()))
                Log.e("this onFailure", "No reponse was received", t)

            }
        }
        requestGamesList(callback)
    }

    fun getGameInfo(liveData: MutableLiveData<GameDto>, gameId: Int) {
        val callback: Callback<GameDto> = object : Callback<GameDto> {
            override fun onResponse(call: Call<GameDto>, response: Response<GameDto>) {
                if (response.isSuccessful) {
                    Log.i("Request game info", "success")
                    liveData.value = response.body()
                } else {

                    Log.i("Request game info", "failure")
                }
            }

            override fun onFailure(call: Call<GameDto>, t: Throwable) {
                Log.i("Request game info", "failure", t)
            }

        }
        requestGameInfo(callback, gameId)
    }


}