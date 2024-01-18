package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.UserCartProduct
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class DeleteProductInCartUseCase(private val shoppingRepository: ShoppingRepository) {
    suspend fun execute(documentId: String) =
        shoppingRepository.deleteProductInCart(documentId)
}