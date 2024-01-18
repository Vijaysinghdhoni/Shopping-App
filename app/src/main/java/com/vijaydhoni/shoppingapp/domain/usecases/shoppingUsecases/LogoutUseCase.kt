package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class LogoutUseCase(private val repository: ShoppingRepository) {
    fun execute() = repository.logout()
}