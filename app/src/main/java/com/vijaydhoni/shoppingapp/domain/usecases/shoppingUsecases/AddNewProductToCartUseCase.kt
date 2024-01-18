package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class AddNewProductToCartUseCase(private val shoppingRepository: ShoppingRepository) {

    suspend fun execute(cartProduct: UserCartProduct) =
        shoppingRepository.addNewProductToCart(cartProduct)
}