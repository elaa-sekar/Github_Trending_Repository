package com.demo.trendinggithubrepo.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@BindingAdapter("image")
fun AppCompatImageView.loadImage(url: String){
    Picasso.get().load(url).into(this)
}