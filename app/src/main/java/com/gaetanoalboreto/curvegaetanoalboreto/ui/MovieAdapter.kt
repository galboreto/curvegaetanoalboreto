package com.gaetanoalboreto.curvegaetanoalboreto.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gaetanoalboreto.curvegaetanoalboreto.databinding.MovieItemListBinding
import com.gaetanoalboreto.curvegaetanoalboreto.model.Movie


class MovieAdapter(context: Context, private val onMovieClicked: (Int) -> Unit = {}) :
    PagedListAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(MovieItemListBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)!! //Asserting this is not null
        holder.binding.movie = item
        holder.binding.root.setOnClickListener { onMovieClicked.invoke(item.id) }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(val binding: MovieItemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}