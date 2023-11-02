package com.example.mmogames


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

val GAME_TITLE_EXTRA: String = "Title"
val GAME_IMAGE_EXTRA = "Image address"

class MainActivity : AppCompatActivity(), GameAdapter.OnGameItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: ProgressBar
    private lateinit var errorTV: TextView
    private val gameAdapter: GameAdapter = GameAdapter(ArrayList<GameDto>(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressIndicator = findViewById(R.id.progress_circular)
        errorTV = findViewById(R.id.error_tv)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = gameAdapter
        val viewModel: RequestGamesViewModel = RequestGamesViewModel()

        viewModel.liveData2.observe(this, object : Observer<LoadingStatus> {
            override fun onChanged(t: LoadingStatus) {
                when (t) {
                    is LoadingStatus.Success -> showLoadedData(t.dataList!!)
                    is LoadingStatus.Error -> setErrorState()
                    is LoadingStatus.Loading -> setLoadingState()
                }
            }

        })
        viewModel.requestGamesList()
    }

    override fun onGameItemClick(game: GameDto) {
        val i: Intent = Intent(this, GameInfoActivity::class.java)
        i.putExtra(GAME_TITLE_EXTRA, game.title)
        i.putExtra(GAME_IMAGE_EXTRA, game.thumbnail)
        startActivity(i)
    }

    private fun setLoadingState() {
        Log.i("Main activity", "loading state")
        recyclerView.visibility = View.INVISIBLE
        progressIndicator.visibility = View.VISIBLE
        errorTV.visibility = View.INVISIBLE
    }

    private fun showLoadedData(gameList: List<GameDto>) {
        Log.i("Main activity", "loaded data")
        recyclerView.visibility = View.VISIBLE
        progressIndicator.visibility = View.INVISIBLE
        errorTV.visibility = View.INVISIBLE
        gameAdapter.gamesArrayList = gameList
        gameAdapter.notifyDataSetChanged()
    }

    private fun setErrorState() {
        recyclerView.visibility = View.INVISIBLE
        progressIndicator.visibility = View.INVISIBLE
        errorTV.visibility = View.VISIBLE
        errorTV.text = "Some error"

    }

}