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

    private val myEndpoint: ApiEndpoint = retrofit.create()

    private fun requestGamesList(callback: Callback<List<GameDto>>) {
        val call: Call<List<GameDto>> = myEndpoint.getGames()
        if (call != null) {
            call.enqueue(callback)
        }
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


}