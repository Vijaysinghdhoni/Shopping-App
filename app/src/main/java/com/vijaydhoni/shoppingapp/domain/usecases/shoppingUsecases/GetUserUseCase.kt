package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetUserUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun execute() = shoppingRepository.getUser()
}