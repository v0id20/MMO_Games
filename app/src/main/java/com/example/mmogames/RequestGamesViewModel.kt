package com.example.mmogames

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RequestGamesViewModel : ViewModel() {
    var liveData2: MutableLiveData<LoadingStatus> =
        MutableLiveData<LoadingStatus>(LoadingStatus.Loading())
    var myService: MyService = MyService()
    fun requestGamesList() {
        myService.getGamesList(liveData2)
        Log.d("getGamesList", "exit request data")
    }
}

sealed class LoadingStatus() {
    class Loading() : LoadingStatus() {
    }

    class Error(val errorMessage: String) : LoadingStatus() {
    }

    class Success(val dataList: List<GameDto>?) : LoadingStatus() {
    }
}