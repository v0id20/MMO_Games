package com.example.mmogames


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
val GAME_TITLE_EXTRA: String = "Title"

class MainActivity : AppCompatActivity(), GameAdapter.OnGameItemClickListener {

    val gamesList: ArrayList<GameDto> = ArrayList<GameDto>()

    private lateinit var myService: MyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        val gameAdapter: GameAdapter = GameAdapter(gamesList, this)
        recyclerView.adapter = gameAdapter
        myService = MyService(gameAdapter)
        myService.getGamesList()
    }

    override fun onGameItemClick(game: GameDto) {
        val i: Intent = Intent(this, GameInfoActivity::class.java)
        i.putExtra(GAME_TITLE_EXTRA, game.title)

        startActivity(i)
    }

}