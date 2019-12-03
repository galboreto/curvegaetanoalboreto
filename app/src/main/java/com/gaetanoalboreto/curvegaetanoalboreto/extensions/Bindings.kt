package com.gaetanoalboreto.curvegaetanoalboreto.extensions

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("moviePosterUrl")
fun ImageView.loadMoviePosterFromUrl(posterUrl: String) =
    this.loadImageFromUrl("https://image.tmdb.org/t/p/w370_and_h556_bestv2$posterUrl")

@BindingAdapter("progressPercentage")
fun ProgressBar.setProgress(percentage: Float) {
    this.progress = percentage.toInt()
}

@BindingAdapter("android:text")
fun TextView.setText(float: Float){
    this.text = float.toInt().toString()
}
