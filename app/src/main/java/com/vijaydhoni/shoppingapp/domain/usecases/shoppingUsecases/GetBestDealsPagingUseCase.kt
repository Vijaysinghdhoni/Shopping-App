package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetBestDealsPagingUseCase(private val repository: ShoppingRepository) {

    fun execute() = repository.getBestDealsPaging()

}