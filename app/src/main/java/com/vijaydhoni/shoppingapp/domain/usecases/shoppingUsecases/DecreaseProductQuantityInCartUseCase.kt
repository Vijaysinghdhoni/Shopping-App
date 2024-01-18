package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class DecreaseProductQuantityInCartUseCase(private val shoppingRepository: ShoppingRepository) {

    suspend fun execute(documentId: String, userCartProduct: UserCartProduct) =
        shoppingRepository.decreaseProductQuantityInCart(documentId, userCartProduct)
}