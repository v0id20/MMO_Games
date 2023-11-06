package com.example.mmogames.gameinfo

import androidx.lifecycle.MutableLiveData
import com.example.mmogames.GameDto
import com.example.mmogames.MyService

class GameInfoViewModel {
    var myService: MyService = MyService()
    var gameInfoLive: MutableLiveData<GameDto> = MutableLiveData()

    fun requestGameInfo(gameId: Int) {
        myService.getGameInfo(gameInfoLive, gameId)
    }
}