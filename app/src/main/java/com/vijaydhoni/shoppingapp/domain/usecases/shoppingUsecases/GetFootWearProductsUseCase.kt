package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetFootWearProductsUseCase(private val shoppingRepository: ShoppingRepository) {

     fun execute() = shoppingRepository.getFootWearProductsPaging()

}