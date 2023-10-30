package com.example.mmogames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class GameAdapter(var gamesArrayList: List<GameDto>) : Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV: TextView = itemView.findViewById(R.id.title_tv)
        val platformTV: TextView = itemView.findViewById(R.id.platform_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.game_item_view, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = gamesArrayList.size


    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.titleTV.text = gamesArrayList.get(position).title
        holder.platformTV.text = gamesArrayList.get(position).genre
    }
}