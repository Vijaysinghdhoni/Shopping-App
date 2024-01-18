package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetMensProductsUseCase(private val shoppingRepository: ShoppingRepository) {

     fun execute() = shoppingRepository.getMensProductsPaging()
}