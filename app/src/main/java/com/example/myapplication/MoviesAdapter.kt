package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.CardviewBinding

class MoviesAdapter(private val movieList : ArrayList<Movies>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private lateinit var binding : CardviewBinding

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CardviewBinding.inflate(inflater)
        val view = inflater.inflate(R.layout.cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.itemView.findViewById<TextView>(R.id.titleTV).text =
            String.format("Title: %s", movieList[position].title)
        holder.itemView.findViewById<TextView>(R.id.ratingTV).text =
            String.format("Rating: %s", movieList[position].rating)
        holder.itemView.findViewById<TextView>(R.id.directorTV).text =
            String.format("Director: %s", movieList[position].director.strip())

        Glide.with(holder.itemView)
            .load(movieList[position].image)
            .into(holder.itemView.findViewById(R.id.thumbnailIV))
    }

    override fun getItemCount() : Int {
        return movieList.size
    }


}