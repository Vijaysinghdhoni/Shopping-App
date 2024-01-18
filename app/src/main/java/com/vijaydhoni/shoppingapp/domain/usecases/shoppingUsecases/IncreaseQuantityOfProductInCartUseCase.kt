package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class IncreaseQuantityOfProductInCartUseCase(private val shoppingRepository: ShoppingRepository) {

    suspend fun execute(documentRefId: String, userCartProduct: UserCartProduct) =
        shoppingRepository.increaseProductQuantityInCart(documentRefId, userCartProduct)
}