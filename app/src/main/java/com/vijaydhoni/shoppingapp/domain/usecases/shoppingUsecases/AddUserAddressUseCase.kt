package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class AddUserAddressUseCase(private val shoppingRepository: ShoppingRepository) {

    suspend fun execute(address: Address) = shoppingRepository.addUserAddress(address)

}