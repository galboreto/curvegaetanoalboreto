package com.gaetanoalboreto.curvegaetanoalboreto.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.gaetanoalboreto.curvegaetanoalboreto.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModel()

    lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MovieAdapter(this) { viewModel.toggleFavorite(it) }
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        viewModel.getPopularMovies().observe(this, Observer {
            adapter.submitList(it)
            if (it.loadedCount != 0) loadingProgressBar.visibility = GONE //Improve
        })
    }
}
