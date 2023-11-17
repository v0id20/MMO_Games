package com.example.mmogames

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RequestGamesViewModel : ViewModel() {

    var requestBody: Array<String> = emptyArray()
    var liveData2: MutableLiveData<LoadingStatus<List<GameDto>>> =
        MutableLiveData<LoadingStatus<List<GameDto>>>(LoadingStatus.Loading<Nothing>())

    var myService: MyService = MyService()

    var filters: ArrayList<String> = arrayListOf()
    var filtersMap: MutableLiveData<MutableMap<Int, Boolean>>? = null
    fun requestGamesList() {
        myService.getGamesList(liveData2)
    }


    private fun createRequest() {
        if (filters.size > 0) {
            requestBody = Array<String>(filters.size, { i: Int -> filters.get(i) })
//            for (i in 0..filters.size - 1) {
//                requestBody.get(i) = filters[i]
//            }
        } else {
            requestBody = emptyArray()
        }
    }

    fun setFilterMap(ar: MutableMap<Int, Boolean>) {
        filtersMap = MutableLiveData<MutableMap<Int, Boolean>>(ar)

    }

    fun addFilter(filter: String) {
        filters.add(filter)
    }

    fun updateFilterMap(i: Int, state: Boolean) {
        filtersMap!!.value!!.set(i, state)
    }

    fun removeFilter(filter: String) {
        if (filters.contains(filter)) {
            filters.remove(filter)
        }
    }

    fun clearFilters() {
        filters.clear()
        requestBody = emptyArray()
    }

    fun filter() {
        createRequest()
        Log.i("RequestGamesViewModel", "printing body: ")
        for (i in requestBody) {
            Log.i("body", i)
        }
        if (requestBody.isNotEmpty()) {
            myService.filterGamesList(liveData2, *requestBody)
        } else {
            myService.getGamesList(liveData2)
        }
    }

}

