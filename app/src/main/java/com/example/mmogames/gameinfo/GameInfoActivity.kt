package com.example.mmogames.gameinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.mmogames.GAME_ID_EXTRA
import com.example.mmogames.GAME_IMAGE_EXTRA
import com.example.mmogames.GAME_TITLE_EXTRA
import com.example.mmogames.GameDto
import com.example.mmogames.R
import com.example.mmogames.screenshotviewer.ScreenshotViewerActivity
import com.squareup.picasso.Picasso

const val SCREENSHOT_ARRAY_EXTRA: String = "screenshot array"

class GameInfoActivity : AppCompatActivity(), OnScreenshotClickListener {
    var gameScreenshotAdapter: GameScreenshotAdapter =
        GameScreenshotAdapter((ArrayList<String>()), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)
        val i: Intent = intent
        val gameId: Int = i.getIntExtra(GAME_ID_EXTRA, 0)
        Log.i("game id", " " + gameId)
        val titleTV: TextView = findViewById(R.id.title_tv)
        titleTV.text = i.getStringExtra(GAME_TITLE_EXTRA)
        val image: ImageView = findViewById(R.id.image)
        val thumbmnail: String? = i.getStringExtra(GAME_IMAGE_EXTRA)
        val viewModel: GameInfoViewModel = GameInfoViewModel()
        viewModel.requestGameInfo(gameId)
        viewModel.gameInfoLive.observe(this, object : Observer<GameDto> {
            override fun onChanged(t: GameDto?) {
                updateViews(t)
            }
        })
        Picasso.get().load(thumbmnail).into(image)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val layoutManager: LinearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = gameScreenshotAdapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    private fun updateViews(game: GameDto?) {
        if (game != null) {
            val descriptionTV: TextView = findViewById(R.id.description_tv)
            descriptionTV.text = game.short_description
            val processorTV: TextView = findViewById(R.id.processor_tv)
            processorTV.text = game.minimum_system_requirements?.processor
            val platformTV: TextView = findViewById(R.id.platform_tv)
            platformTV.text = game.platform
            val releaseDateTV: TextView = findViewById(R.id.release_date_tv)
            releaseDateTV.text = game.release_date
            val publisherTV: TextView = findViewById(R.id.publisher_tv)
            publisherTV.text = game.publisher
            val developerTV: TextView = findViewById(R.id.issuer_tv)
            developerTV.text = game.developer
            if (game.screenshotArray != null) {
                gameScreenshotAdapter.screenshotArray = game.screenshotArray
                gameScreenshotAdapter.notifyDataSetChanged()
            }
            if (game.minimum_system_requirements != null) {
                Log.i(
                    "GameInfoActivity",
                    "game requirements true=null false=not null: " + (game.minimum_system_requirements == null).toString()
                )
                Log.i(
                    "game requirements not null ",
                    game.minimum_system_requirements?.graphics.toString()
                )
                val requirementsLayout: ConstraintLayout = findViewById(R.id.requirements_layout)
                requirementsLayout.visibility = View.VISIBLE
                val processorTV: TextView = findViewById(R.id.processor_tv)
                processorTV.text = game.minimum_system_requirements?.processor
                val graphicsTV: TextView = findViewById(R.id.graphics_tv)
                graphicsTV.text = game.minimum_system_requirements?.graphics
                val memoryTV: TextView = findViewById(R.id.memory_tv)
                memoryTV.text = game.minimum_system_requirements?.memory
                val storageTV: TextView = findViewById(R.id.storage_tv)
                storageTV.text = game.minimum_system_requirements?.storage
                val osTV: TextView = findViewById(R.id.os_tv)
                osTV.text = game.minimum_system_requirements?.os
            }
        }
    }


    private fun setLoadingState() {

    }

    override fun onScreenshotClick(screenshotArray: ArrayList<String>) {
        Log.i("GameInfoActivity", "screenshotarray: " + screenshotArray?.size.toString())
        val i: Intent = Intent(this, ScreenshotViewerActivity::class.java)
        i.putStringArrayListExtra(SCREENSHOT_ARRAY_EXTRA, screenshotArray)
        startActivity(i)
    }
}

interface OnScreenshotClickListener {
    fun onScreenshotClick(stringArray: ArrayList<String>)
}