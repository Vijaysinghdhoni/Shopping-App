package com.vijaydhoni.shoppingapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserCartProductsList(
    val products: List<UserCartProduct>
) : Parcelable
