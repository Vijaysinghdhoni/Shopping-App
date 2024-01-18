package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetRecentOrderUseCase(private val repository: ShoppingRepository) {
    suspend fun execute() = repository.getRecentOrder()
}