package com.example.movierentalkotlin.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movierentalkotlin.R

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .placeholder(R.drawable.baseline_image_not_supported_24)
        .error(R.drawable.baseline_image_not_supported_24)
        .into(imageView)
}