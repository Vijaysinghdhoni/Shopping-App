package com.vijaydhoni.shoppingapp.data.model

import android.os.Parcelable
import com.vijaydhoni.shoppingapp.data.util.OrderStatus
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random.Default.nextLong

@Parcelize
data class Order(
    val orderStatus: String = "",
    val totalPrice: String = "",
    val cartProducts: List<UserCartProduct> = emptyList(),
    val address: Address = Address(),
    val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(Date()),
    val orderId: Long = nextLong(0, 100_000_000_000)
) : Parcelable
