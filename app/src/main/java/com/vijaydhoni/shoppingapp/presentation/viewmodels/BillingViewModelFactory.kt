package com.vijaydhoni.shoppingapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.GetUserAddressesUseCase
import com.vijaydhoni.shoppingapp.domain.usecases.shoppingUsecases.PlaceOrderUseCase

class BillingViewModelFactory(
    private val getUserAddressesUseCase: GetUserAddressesUseCase,
    private val placeOrderUseCase: PlaceOrderUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BillingViewmodel(getUserAddressesUseCase, placeOrderUseCase) as T
    }

}