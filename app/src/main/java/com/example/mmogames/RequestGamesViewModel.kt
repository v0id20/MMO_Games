package com.example.mmogames

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RequestGamesViewModel : ViewModel() {

    private var requestBody: Array<String> = emptyArray()
    var liveData2: MutableLiveData<LoadingStatus<List<GameDto>>> =
        MutableLiveData<LoadingStatus<List<GameDto>>>(LoadingStatus.Loading<Nothing>())
    private val myService: MyService = MyService()
    private var filters: ArrayList<String> = arrayListOf()
    var filtersMap: MutableLiveData<MutableMap<Int, Boolean>>? = null
    var platformsMap: MutableLiveData<MutableMap<Int, Boolean>>? = null
    private var sortBy: String? = null
    fun requestGamesList() {
        myService.getGamesList(liveData2)
    }

    fun filterAndSort() {
        createFilterRequest()
        if (sortBy != null) {
            if (requestBody.isNotEmpty()) {
                myService.filterAndSortGames(liveData2, sortBy!!, *requestBody)
            } else {
                Log.i("RequestGamesViewModel filterAndSort", "sortBy" + sortBy)
                myService.sortGamesList(liveData2, sortBy!!)
            }
        } else if (requestBody.isNotEmpty()) {
            myService.filterGamesList(liveData2, *requestBody)
        } else {
            myService.getGamesList(liveData2)
        }
    }

    private fun createFilterRequest() {
        if (filters.size > 0) {
            requestBody = Array<String>(filters.size, { i: Int -> filters.get(i) })
        } else {
            requestBody = emptyArray()
        }
    }

    fun setFilterMap(ar: Array<Int>) {
        filtersMap = MutableLiveData(ar.associateWith { it -> false } as MutableMap<Int, Boolean>)
    }

    private fun addFilter(filter: String) {
        if (filter.contains(" ")) filters.add(filter.replace(" ", "-")) else filters.add(filter)
    }

    private fun updateFilterMap(i: Int, state: Boolean) {
        filtersMap!!.value!!.set(i, state)
    }

    private fun removeFilter(filter: String) {
        if (filters.contains(filter)) {
            filters.remove(filter)
        } else if (filter.contains(" ")) {
            filters.remove(filter.replace(" ", "-"))
        }
    }

    fun clearFilters(ar: Array<Int>) {
        filters.clear()
        setFilterMap(ar)
        requestBody = emptyArray()
        sortBy=null
    }


    fun kek(id: Int, text: String): Int {
//        if (filtersMap?.value?.contains(id) == true) {
//
//        } else if (platformsMap?.value?.contains(id)==true){
//
//        }

        if (filtersMap?.value?.get(id) == false) {
            addFilter(text)
            updateFilterMap(id, true)
            return R.color.black
        } else {
            updateFilterMap(id, false)
            removeFilter(text)
            return com.google.android.material.R.color.mtrl_btn_transparent_bg_color
        }
    }

    fun kek2(text: String){
        sortBy = text.lowercase()
    }

}

