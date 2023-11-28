package com.example.mmogames


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mmogames.gameinfo.GameInfoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

val GAME_ID_EXTRA: String = "Game ID"
val GAME_TITLE_EXTRA: String = "Title"
val GAME_IMAGE_EXTRA = "Image address"

class MainActivity : AppCompatActivity(), GameAdapter.OnGameItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: ProgressBar
    private lateinit var errorTV: TextView
    private lateinit var errorIcon: ImageView
    private val gameAdapter: GameAdapter = GameAdapter(ArrayList<GameDto>(), this)
    private lateinit var viewModel: RequestGamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressIndicator = findViewById(R.id.progress_circular)
        errorTV = findViewById(R.id.error_tv)
        errorIcon = findViewById(R.id.error_icon)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = gameAdapter
        viewModel = ViewModelProvider(this)[com.example.mmogames.RequestGamesViewModel::class.java]

        val fabButton: FloatingActionButton = findViewById(R.id.filter_button)
        fabButton.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                //open filter fragment
                val filtersFragment: FilterBottomSheetFragment = FilterBottomSheetFragment()
                filtersFragment.show(getSupportFragmentManager(), "SOME STUPID TAG")
            }
        }
        )

        viewModel.requestGamesList()
        viewModel.liveData2.observe(this, object : Observer<LoadingStatus<List<GameDto>>> {
            override fun onChanged(t: LoadingStatus<List<GameDto>>) {
                when (t) {
                    is LoadingStatus.Success -> showLoadedData(t.dataList!!)
                    is LoadingStatus.Error<*> -> setErrorState()
                    is LoadingStatus.Loading<*> -> setLoadingState()
                }
            }
        })
    }

    override fun onGameItemClick(game: GameDto) {
        val i: Intent = Intent(this, GameInfoActivity::class.java)
        i.putExtra(GAME_ID_EXTRA, game.id)
        i.putExtra(GAME_TITLE_EXTRA, game.title)
        i.putExtra(GAME_IMAGE_EXTRA, game.thumbnail)
        startActivity(i)
    }

    fun setLoadingState() {
        recyclerView.visibility = View.INVISIBLE
        progressIndicator.visibility = View.VISIBLE
        errorTV.visibility = View.INVISIBLE
        errorIcon.visibility = View.INVISIBLE
    }

    private fun showLoadedData(gameList: List<GameDto>) {
        Log.i("Main activity", "loaded data")
        recyclerView.visibility = View.VISIBLE
        progressIndicator.visibility = View.INVISIBLE
        errorTV.visibility = View.INVISIBLE
        errorIcon.visibility = View.INVISIBLE
        gameAdapter.gamesArrayList = gameList
        gameAdapter.notifyDataSetChanged()
    }

    private fun setErrorState() {
        recyclerView.visibility = View.INVISIBLE
        progressIndicator.visibility = View.INVISIBLE
        errorTV.visibility = View.VISIBLE
        errorTV.text = resources.getText(R.string.error_try_again)
        errorIcon.visibility = View.VISIBLE
    }

    private fun retry() {
        viewModel.requestGamesList()
    }

}