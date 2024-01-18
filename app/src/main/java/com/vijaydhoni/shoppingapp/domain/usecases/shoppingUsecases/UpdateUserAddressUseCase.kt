package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class UpdateUserAddressUseCase(private val shoppingRepository: ShoppingRepository) {

    suspend fun execute(newAddress: Address, addressID: String) =
        shoppingRepository.updateUserAddress(newAddress, addressID)
}