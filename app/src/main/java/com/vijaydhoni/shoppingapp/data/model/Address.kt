package com.vijaydhoni.shoppingapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val addressLocation: String,
    val fullName: String,
    val fullAddress: String,
    val phone: String,
    val city: String,
    val state: String
) : Parcelable {
    constructor() : this("", "", "", "", "", "")
}
