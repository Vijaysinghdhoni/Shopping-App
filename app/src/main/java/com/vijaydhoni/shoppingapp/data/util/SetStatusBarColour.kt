package com.vijaydhoni.shoppingapp.data.util

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.vijaydhoni.shoppingapp.R

fun setStatusBarColour(activity: AppCompatActivity?, colourId: Int) {
    activity?.window?.statusBarColor = ContextCompat.getColor(
        activity!!,
        colourId
    )
}