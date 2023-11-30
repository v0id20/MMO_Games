package com.example.mmogames.gameinfo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mmogames.R
import com.squareup.picasso.Picasso

class GameScreenshotAdapter(
    var screenshotArray: ArrayList<String>?,
    val onScreenshotClickListener: OnScreenshotClickListener
) :
    RecyclerView.Adapter<GameScreenshotAdapter.GameScreenshotViewHolder>() {

    class GameScreenshotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameScreenshotViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.screenshot_item, parent, false)
        view.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                Log.i("Adapter onClick", screenshotArray?.size.toString())
                onScreenshotClickListener.onScreenshotClick(screenshotArray ?: ArrayList<String>())
            }
        })
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