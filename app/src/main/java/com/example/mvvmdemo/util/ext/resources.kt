package com.example.mvvmdemo.util.ext

import androidx.core.content.ContextCompat
import com.example.mvvmdemo.App

fun Int.asColor() = ContextCompat.getColor(App.instance, this)
fun Int.asDrawable() = ContextCompat.getDrawable(App.instance, this)

