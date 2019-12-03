package com.gaetanoalboreto.curvegaetanoalboreto.extensions

import android.widget.ImageView
import com.gaetanoalboreto.curvegaetanoalboreto.R
import com.squareup.picasso.Picasso

fun ImageView.loadImageFromUrl(url: String) =
    Picasso.get().load(url).placeholder(R.drawable.placeholder).into(this)
