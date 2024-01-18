package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class DeleteUserAddressUSeCase(private val shoppingRepository: ShoppingRepository) {

    suspend fun execute(documentId: String) = shoppingRepository.deleteUSerAddress(documentId)

}