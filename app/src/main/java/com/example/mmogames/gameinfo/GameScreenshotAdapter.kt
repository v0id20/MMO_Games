package com.example.mmogames.gameinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mmogames.R
import com.squareup.picasso.Picasso

class GameScreenshotAdapter(var screenshotArray: ArrayList<String>?) :
    RecyclerView.Adapter<GameScreenshotAdapter.GameScreenshotViewHolder>() {

    class GameScreenshotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameScreenshotViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.screenshot_item, parent, false)
        return GameScreenshotViewHolder(view)
    }

    override fun getItemCount(): Int {
        return screenshotArray?.size ?: 0
    }

    override fun onBindViewHolder(holder: GameScreenshotViewHolder, position: Int) {
        Picasso.get().load(screenshotArray?.get(position))
            .placeholder(R.drawable.screenshot_placeholder)
            .into((holder as GameScreenshotViewHolder).imageView)
    }
}