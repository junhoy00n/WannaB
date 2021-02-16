package com.example.wannab

import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter

object DataBindingAdapter {
    @JvmStatic
    @BindingAdapter("src")
    fun bindImage(view: ImageView, item: Int) {
        view.setImageResource(item)
    }

    @JvmStatic
    @BindingAdapter("src")
    fun bindImage(view: ImageView, code: ByteArray) {
        view.setImageBitmap(BitmapFactory.decodeByteArray(code, 0, code.size))
    }
}