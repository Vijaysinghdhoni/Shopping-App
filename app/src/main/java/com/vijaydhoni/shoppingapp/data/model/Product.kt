package com.vijaydhoni.shoppingapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val productName: String,
    val productCategory: String,
    val productDescription: String? = null,
    val price: Float,
    val offerPercentage: Float? = null,
    val sizes: List<String>? = null,
    val colours: List<Int>? = null,
    val images: List<String>
) : Parcelable {
    constructor() : this("0", "", "", price = 0f, images = emptyList())
}
