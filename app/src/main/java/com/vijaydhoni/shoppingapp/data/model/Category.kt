package com.vijaydhoni.shoppingapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Category(
    val name: String,
    val image: Int = 0
) : Parcelable
