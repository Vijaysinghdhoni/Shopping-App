package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetSearchedProductUseCase(private val repository: ShoppingRepository) {

    suspend fun execute(productNameQuery: String) = repository.getSearchedProduct(productNameQuery)

}