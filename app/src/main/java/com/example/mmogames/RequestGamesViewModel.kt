package com.example.mmogames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val PLATFORM_ALL: String = "all"
const val PLATFORM_PC: String = "PC"
const val PLATFORM_BROWSER = "Broswer"

class RequestGamesViewModel : ViewModel() {

    private var requestBody: Array<String> = emptyArray()
    val liveData2: MutableLiveData<LoadingStatus<List<GameDto>>> =
        MutableLiveData<LoadingStatus<List<GameDto>>>(LoadingStatus.Loading<Nothing>())
    private val myService: MyService = MyService()
    private var filters: ArrayList<String> = arrayListOf()
    private var platforms: ArrayList<String> = arrayListOf()
    private var plat: String? = null
    var sortBy: String? = null
    var checkedId: Int? = null
    var filterTypeList: List<Filter>? = null

    fun requestGamesList() {
        myService.getGamesList(liveData2)
    }

    fun filterAndSort() {
        liveData2.value = LoadingStatus.Loading<Nothing>()
        createFilterRequest()
        myService.ultimateFilter(liveData2, sortBy, plat, *requestBody)
    }

    private fun createFilterRequest() {
        if (filters.size > 0) {
            requestBody = Array<String>(filters.size, { i: Int -> filters.get(i) })
        } else {
            requestBody = emptyArray()
        }
        if (platforms.size == 2) {
            plat = PLATFORM_ALL
        } else if (platforms.size > 0) {
            plat = platforms.get(0).lowercase()
        }
    }

    fun setFilterMap(ar: Map<Int, String>) {
        filterTypeList = ar.map {
            if (it.value.equals(PLATFORM_PC) || it.value.equals(PLATFORM_BROWSER)) Filter(
                it.key,
                it.value,
                FilterType.PLATFORM,
                false
            ) else Filter(it.key, it.value, FilterType.CATEGORY, false)
        }
    }

    private fun addFilter(platform: String, array: ArrayList<String>) {
        if (platform.contains(" ")) array.add(platform.replace(" ", "-")) else array.add(platform)
    }

    private fun removeFilter(filter: String, ar: ArrayList<String>) {
        if (ar.contains(filter)) {
            ar.remove(filter)
        } else if (filter.contains(" ")) {
            ar.remove(filter.replace(" ", "-"))
        }
    }

    fun clearFilters(ar: Map<Int, String>) {
        filters.clear()
        setFilterMap(ar)
        requestBody = emptyArray()
        sortBy = null
        platforms.clear()
        plat = null
    }

    fun kek(id: Int): Int {
        val k: Filter? = filterTypeList?.find { it.id == id }
        if (k != null) {
            if (k.status) {
                k.status = false
                when (k.type) {
                    FilterType.CATEGORY -> removeFilter(k.text, filters)
                    FilterType.PLATFORM -> removeFilter(k.text, platforms)
                }
                return com.google.android.material.R.color.mtrl_btn_transparent_bg_color
            } else {
                k.status = true
                when (k.type) {
                    FilterType.CATEGORY -> addFilter(k.text, filters)
                    FilterType.PLATFORM -> addFilter(k.text, platforms)
                }
                return R.color.black
            }
        } else {
            return com.google.android.material.R.color.mtrl_btn_transparent_bg_color
        }
    }

    fun kek2(text: String, checkedId: Int) {
        if (text.contains(" ")) {
            sortBy = text.replace(" ", "-").lowercase()
        } else {
            sortBy = text.lowercase()
        }
        this.checkedId = checkedId
    }

}

