package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.Order
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class PlaceOrderUseCase(private val repository: ShoppingRepository) {
    suspend fun execute(order: Order) = repository.placeOrder(order)
}