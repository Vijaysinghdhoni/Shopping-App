package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetAllOrdersUseCase(private val repository: ShoppingRepository) {
    suspend fun execute() = repository.getAllOrders()
}