package com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases

import com.vijaydhoni.shoppingapp.data.model.Address
import com.vijaydhoni.shoppingapp.domain.repositorys.ShoppingRepository

class GetCurrentAddressFromUserAddressesUseCase(private val repository: ShoppingRepository) {

    suspend fun execute(address: Address) = repository.getCurrentAddresFromUserAddress(address)

}