package com.example.mmogames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val gamesList: ArrayList<GameDto> = ArrayList<GameDto>()

    lateinit var  myService: MyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gamesList.add(GameDto(10, "Dota 2", "sdfgwertwerty", "MOBA", "", "", "Windows", "Windows", "", "2013", "" ))
        gamesList.add(GameDto(11, "League of Legends", "sdfgwerakeiussrty", "MOBA", "", "", "Windows", "", "", "", "" ))
        gamesList.add(GameDto(12, "Heroes of Newerth", "ertkngcjhgfdfnmny", "MOBA", "", "", "PC (Windows)", "Windows", "", "2013", "" ))
        gamesList.add(GameDto(13, "PUBG", "kolokawart", "MOBA", "", "", "PC (Windows", "", "", "", "" ))
        val gameAdapter: GameAdapter = GameAdapter(gamesList)
        myService = MyService(gameAdapter)
        myService.getGamesList()
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = gameAdapter
    }
}