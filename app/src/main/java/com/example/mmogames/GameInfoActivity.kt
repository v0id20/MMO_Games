package com.example.mmogames

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class GameInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)
        val i: Intent = intent
        val titleTV: TextView = findViewById(R.id.title_tv)
        titleTV.text = i.getStringExtra(GAME_TITLE_EXTRA)
        val image: ImageView = findViewById(R.id.image)
        val thumbmnail: String? = i.getStringExtra(GAME_IMAGE_EXTRA)

        Picasso.get().load(thumbmnail).into(image)
    }
}