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

    private fun getFilteredGamesList(callback: Callback<List<GameDto>>, vararg tag: String) {
        val call: Call<List<GameDto>>? = myEndpoint.getFiltered(*tag)
        call?.enqueue(callback)
    }

    private fun getSortedGamesList(callback: Callback<List<GameDto>>, sortBy: String) {
        val call: Call<List<GameDto>>? = myEndpoint.getSorted(sortBy)
        call?.enqueue(callback)
    }

    private fun getFilteredAndSortedGamesList(
        callback: Callback<List<GameDto>>,
        sortBy: String,
        vararg tag: String
    ) {
        val call: Call<List<GameDto>>? = myEndpoint.getFilteredAndSorted(sortBy, *tag)
        call?.enqueue(callback)
    }

    fun getGamesList(liveData: MutableLiveData<LoadingStatus<List<GameDto>>>) {
        val callback: Callback<List<GameDto>> = object : Callback<List<GameDto>> {

            override fun onResponse(call: Call<List<GameDto>>, response: Response<List<GameDto>>) {
                if (response.isSuccessful) {
                    liveData.setValue(LoadingStatus.Success(response.body()))

                } else {
                    liveData.setValue(LoadingStatus.Error<Nothing>("Response unsuccessful"))
                    Log.i("onResponse", "Response unsuccessful")
                }
            }

            override fun onFailure(call: Call<List<GameDto>>, t: Throwable) {
                liveData.setValue(LoadingStatus.Error<Nothing>(t.message.toString()))
                Log.e("this onFailure", "No response was received", t)
            }
        }
        requestGamesList(callback)
    }

    fun getGameInfo(liveData: MutableLiveData<GameDto>, gameId: Int) {
        val callback: Callback<GameDto> = object : Callback<GameDto> {
            override fun onResponse(call: Call<GameDto>, response: Response<GameDto>) {

                if (response.isSuccessful) {
                    Log.i("Request game info", "success")
                    Log.i(
                        "onResponse, checking system requirements ",
                        (response.body()?.minimum_system_requirements == null).toString()
                    )
                    Log.i(
                        "onResponse, checking system requirements, graphics ", " " +
                                response.body()?.minimum_system_requirements?.graphics
                    )
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

    fun filterGamesList(
        liveData: MutableLiveData<LoadingStatus<List<GameDto>>>,
        vararg tag: String
    ) {
        var callback: Callback<List<GameDto>> = ResponseLoader(liveData)
        getFilteredGamesList(callback, *tag)
    }


    fun sortGamesList(liveData: MutableLiveData<LoadingStatus<List<GameDto>>>, sortBy: String) {
        var callback: Callback<List<GameDto>> = ResponseLoader(liveData)
        getSortedGamesList(callback, sortBy)
    }

    fun filterAndSortGames(
        liveData: MutableLiveData<LoadingStatus<List<GameDto>>>,
        sortBy: String,
        vararg tag: String
    ) {
        var callback: Callback<List<GameDto>> = ResponseLoader(liveData)
        getFilteredAndSortedGamesList(callback, sortBy, *tag)
    }
}

class ResponseLoader(val liveData: MutableLiveData<LoadingStatus<List<GameDto>>>) :
    Callback<List<GameDto>> {

    override fun onResponse(call: Call<List<GameDto>>, response: Response<List<GameDto>>) {
        if (response.isSuccessful) {
            liveData.setValue(LoadingStatus.Success(response.body()))
        } else {
            liveData.setValue(LoadingStatus.Error<Nothing>("Response unsuccessful"))
        }
    }

    override fun onFailure(call: Call<List<GameDto>>, t: Throwable) {
        liveData.setValue(LoadingStatus.Error<Nothing>("Response unsuccessful"))
    }

}

sealed class LoadingStatus<out T>() {
    class Loading<T>() : LoadingStatus<Nothing>() {
    }

    class Error<T>(val errorMessage: String) : LoadingStatus<Nothing>() {
    }

    class Success<T>(val dataList: T?) : LoadingStatus<T>() {
    }
}
