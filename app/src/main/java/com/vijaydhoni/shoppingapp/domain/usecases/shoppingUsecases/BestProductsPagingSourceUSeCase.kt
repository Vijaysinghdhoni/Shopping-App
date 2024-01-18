package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class BestProductsPagingSourceUSeCase(private val repository: ShoppingRepository) {
    fun execute() = repository.getBestProductsPaging()
}