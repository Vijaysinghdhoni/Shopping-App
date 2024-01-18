package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetAccessoriesProductsUseCase(private val shoppingRepository: ShoppingRepository) {

     fun execute() = shoppingRepository.getAccessoriesProductsPaging()

}